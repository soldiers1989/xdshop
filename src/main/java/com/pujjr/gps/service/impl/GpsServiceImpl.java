package com.pujjr.gps.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pujjr.gps.dal.dao.GpsBrandMapper;
import com.pujjr.gps.dal.dao.GpsStoreBranchMapper;
import com.pujjr.gps.dal.dao.RecordSegUploadResultMapper;
import com.pujjr.gps.dal.domain.ApplyTenant;
import com.pujjr.gps.dal.domain.GpsBrand;
import com.pujjr.gps.dal.domain.GpsStoreBranch;
import com.pujjr.gps.dal.domain.RecordSegUploadResult;
import com.pujjr.gps.dal.domain.SegUploadData;
import com.pujjr.gps.dal.domain.SegUploadDataIntegration;
import com.pujjr.gps.service.GpsService;
import com.pujjr.gps.service.base.BaseService;
import com.pujjr.gps.util.Utils;
import com.pujjr.gps.vo.GpsBranchVo;


@Service
public class GpsServiceImpl extends BaseService<GpsService> implements GpsService {
	@Value("${rest.pcms.url}")
	private String pcmsUrl;
	@Value("${rest.pcms-query.url}")
	private String pcmsQueryUrl;
	@Value("${rest.seg.url}")
	private String segUrl;
	@Value("${segUpload.md5Key}")
	private String segMd5Key;
	@Value("${segUpload.successCode}")
	private String successCode;
	@Autowired
	private GpsBrandMapper gpsBrandMapperImpl;
	@Autowired
	private GpsStoreBranchMapper gpsStoreBranchDao;
	@Autowired
	private RecordSegUploadResultMapper recordSegUploadResultDao;

	@Override
	public List<GpsBrand> getGpsBrandList() {
		return gpsBrandMapperImpl.selectAll();
	}

	@Override
	public List<GpsBranchVo> getAllBranch() {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("访问地址：");
		logger.info(pcmsUrl + File.separator + "public"+ File.separator +"sysbranch"+File.separator+ "getDealerList");
		ResponseEntity responseEntity = restTemplate.exchange(pcmsUrl + File.separator + "public"+ File.separator +"sysbranch"+File.separator+ "getDealerList", HttpMethod.GET, null, Object.class, map);
		// 信贷系统返回所有经销商信息
		List<Map<String, Object>> branchList = (List<Map<String, Object>>) responseEntity.getBody();
		List<GpsBranchVo> gpsBranchVoList = new ArrayList<GpsBranchVo>();
		for (int i = 0; i < branchList.size(); i++) {
			Map<String, Object> branchFromPcms = branchList.get(i);
			GpsBranchVo gpsBranchVo = new GpsBranchVo();
			gpsBranchVo.setBranchId(branchFromPcms.get("branchCode") + "");
			gpsBranchVo.setBranchName(branchFromPcms.get("branchFullname") + "");
			gpsBranchVo.setEnabled(branchFromPcms.get("enabled") + "");
			gpsBranchVoList.add(gpsBranchVo);
		}
		return gpsBranchVoList;
	}
	
	String temp;
	@Override
	public void uploadYesterdayDataToSeg() {
		List<GpsStoreBranch> list = gpsStoreBranchDao.getYesterdayUpploadToSeg();
		//测试数据
//		List<GpsStoreBranch> list = new ArrayList<GpsStoreBranch>();
//		GpsStoreBranch test = new GpsStoreBranch();
//		test.setGpsId("64862934584");
//		test.setApplyId("A2031801290002ICBC");
//		list.add(test);
		
//		List<GpsStoreBranch> list = getTestData();
		
		//获取所有的区域 sysARE
		RestTemplate restTemplate = new RestTemplate();
		//获取信贷系统的地址编码与对应信息
		ResponseEntity<ArrayList> responseEntityArea = restTemplate.getForEntity(pcmsQueryUrl + "/service/sysarea" , ArrayList.class);
		ArrayList areaList = responseEntityArea.getBody();
		Map<String,String> areaMap = new HashMap<String,String>();
		//areaList映射到hashMap，方便后面取值
		for(int i=0;i<areaList.size();i++){
			LinkedHashMap<String, Object> areaItem = (LinkedHashMap<String, Object>)areaList.get(i);
			areaMap.put((String)areaItem.get("id"), (String)areaItem.get("areaName"));
		}
		//更新appId获取信贷系统客户信息
		for(GpsStoreBranch item : list){
			try{
				if(item.getApplyId() != null && ! item.getApplyId().equals("")){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("appId", item.getApplyId());
					//查询订单信息
					ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(pcmsQueryUrl + "/service/apply/" + item.getApplyId(), JSONObject.class, map);
					JSONObject apply = JSONObject.parseObject(responseEntity.getBody().toString());
					JSONObject tenant = JSONObject.parseObject(apply.get("tenant").toString());
					Object custName = tenant.get("name");
					if(custName != null){
						ApplyTenant tenantVo = JSON.parseObject(apply.get("tenant").toString(), ApplyTenant.class);
						SegUploadDataIntegration uploadData = new SegUploadDataIntegration();
						try {
							//赛格秘钥
							String sign = Utils.MD5(segMd5Key);
							uploadData.setSign(sign);
						} catch (Exception e) {
							e.printStackTrace();
						}
						//设置合同号，客户姓名
						uploadData.setCustomerName(custName.toString());
						List<SegUploadData> data = new ArrayList<SegUploadData>();
						//pcms-query-web查询合同号
						ResponseEntity<JSONObject> responseEntityContractInfo = restTemplate.getForEntity(pcmsQueryUrl + "/service/task/querySignInfo/" + item.getApplyId(), JSONObject.class, map);
						JSONObject signContractInfo = JSONObject.parseObject(responseEntityContractInfo.getBody().toString());
						String contractNo = signContractInfo.getString("contractNo");
						uploadData.setContractNo(contractNo);
						
						uploadData.setCallLetter(item.getGpsId());
						//现地址
						SegUploadData seg1 = new SegUploadData();
						String procVince1 = areaMap.get(tenantVo.getAddrProvince()).replace("省", "");
						String city1 = "";
						if(procVince1.contains("北京") || procVince1.contains("天津") || procVince1.contains("上海") || procVince1.contains("重庆")){
							city1 = "直辖市";
						}else{
							city1 = areaMap.get(tenantVo.getAddrCity());
						}
						//海南 直辖单位
						if(procVince1.equals("海南")&&city1.equals("直辖单位")){
							city1 = "省直辖县级行政单位";
						}
						
						String district1 = areaMap.get(tenantVo.getAddrCounty()).replace("市辖区", "");
						seg1.setProvince(procVince1);
						seg1.setCity(city1);
						seg1.setDistrict(district1);
						//这三procVince1 + city1 + district1个加起来的数据不会超过42位
						String location1 = procVince1 + city1 + district1;
						String location1_ = procVince1 + city1 + district1 + tenantVo.getAddrExt();
						if(location1_.length()>30){//赛格数据限制最长42位
							int distance = 30-location1.length();
							String addrExt = tenantVo.getAddrExt();
							seg1.setAddress(addrExt.substring(0, distance));
						}else{
							seg1.setAddress(tenantVo.getAddrExt());
						}
						seg1.setAddressType("now");
						data.add(seg1);
						//工作地址
						SegUploadData seg2 = new SegUploadData();
						String province2 = areaMap.get(tenantVo.getUnitAddrProvince()).replace("省", "");
						
						String city2 = "";
						if(province2.contains("北京") || province2.contains("天津") || province2.contains("上海") || province2.contains("重庆")){
							city2 = "直辖市";
						}else{
							city2 = areaMap.get(tenantVo.getUnitAddrCity());
						}
						
						if(province2.equals("海南")&&city2.equals("直辖单位")){
							city2 = "省直辖县级行政单位";
						}
						
						String district2 = areaMap.get(tenantVo.getUnitAddrCounty()).replace("市辖区", "");
						seg2.setProvince(province2);
						seg2.setCity(city2);
						seg2.setDistrict(district2);
						String unitAddrExt = tenantVo.getUnitAddrExt();
						String location2 = province2 + city2 + district2;
						String location2_ = province2 + city2 + district2 + unitAddrExt;
						if(location2_.length()>30){
							int distance = 30- location2.length();
							unitAddrExt = unitAddrExt.substring(0, distance);
						}
						seg2.setAddress(unitAddrExt);
						
						seg2.setAddressType("work");
						data.add(seg2);
						uploadData.setData(data);
						//把uploadData转为json
						JSONObject target = (JSONObject)JSON.toJSON(uploadData);
						JSONObject responseReturn = restTemplate.postForObject(segUrl, target, JSONObject.class);
						//判断返回结果
						String returnCode = responseReturn.getString("returnCode");
						if(returnCode.equals(successCode)){
							JSONArray datas = responseReturn.getJSONArray("datas");
							if(datas != null && datas.size() >0){//解决县/区与赛格不匹配问题，直接传入""字符串
								JSONObject data1 = datas.getJSONObject(0);
								String reason = data1.getString("reason");
								if(reason.contains("找不到所属县/区")){
									seg1.setDistrict("");
									seg2.setDistrict("");
									JSONObject target_ = (JSONObject)JSON.toJSON(uploadData);
									responseReturn = restTemplate.postForObject(segUrl, target_, JSONObject.class);
									returnCode = responseReturn.getString("returnCode");
								}
							}
						}
						RecordSegUploadResult record = new RecordSegUploadResult();
						record.setAppId(item.getApplyId());
						record.setGpsNo(item.getGpsId());
						record.setReturnCode(returnCode);
						record.setReturnMsg(responseReturn.getString("returnMsg").toString());
						record.setUploadTimes(1);
						record.setUploadDate(new Date());
						record.setUploadData(target.toString());
						record.setReturnData(responseReturn.toString());
						recordSegUploadResultDao.insert(record);
					}else{
						
					}
				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				RecordSegUploadResult record = new RecordSegUploadResult();
				record.setAppId(item.getApplyId());
				record.setGpsNo(item.getGpsId());
				record.setUploadTimes(1);
				record.setUploadDate(new Date());
				recordSegUploadResultDao.insert(record);
			}
			
		}
	}
	
	
	@Override
	public void uploadAllDataToSeg() {
		// TODO Auto-generated method stub
		//获取GPS库存中已安装的gps信息
		List<GpsStoreBranch> list = gpsStoreBranchDao.getAlluploadDataToSeg();
		
		//测试数据
//		List<GpsStoreBranch> list = new ArrayList<GpsStoreBranch>();
//		GpsStoreBranch test = new GpsStoreBranch();
//		test.setGpsId("64862934584");
//		test.setApplyId("A2031801290002ICBC");
//		list.add(test);
		
//		List<GpsStoreBranch> list = getTestData();
		
		//获取所有的区域 sysARE
		RestTemplate restTemplate = new RestTemplate();
		//获取信贷系统的地址编码与对应信息
		ResponseEntity<ArrayList> responseEntityArea = restTemplate.getForEntity(pcmsQueryUrl + "/service/sysarea" , ArrayList.class);
		ArrayList areaList = responseEntityArea.getBody();
		Map<String,String> areaMap = new HashMap<String,String>();
		//areaList映射到hashMap，方便后面取值
		for(int i=0;i<areaList.size();i++){
			LinkedHashMap<String, Object> areaItem = (LinkedHashMap<String, Object>)areaList.get(i);
			areaMap.put((String)areaItem.get("id"), (String)areaItem.get("areaName"));
		}
		//更新appId获取信贷系统客户信息
		for(GpsStoreBranch item : list){
			try{
				if(item.getApplyId() != null && ! item.getApplyId().equals("")){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("appId", item.getApplyId());
					//查询订单信息
					ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(pcmsQueryUrl + "/service/apply/" + item.getApplyId(), JSONObject.class, map);
					JSONObject apply = JSONObject.parseObject(responseEntity.getBody().toString());
					JSONObject tenant = JSONObject.parseObject(apply.get("tenant").toString());
					Object custName = tenant.get("name");
					if(custName != null){
						ApplyTenant tenantVo = JSON.parseObject(apply.get("tenant").toString(), ApplyTenant.class);
						SegUploadDataIntegration uploadData = new SegUploadDataIntegration();
						try {
							//赛格秘钥
							String sign = Utils.MD5(segMd5Key);
							uploadData.setSign(sign);
						} catch (Exception e) {
							e.printStackTrace();
						}
						//设置合同号，客户姓名
						uploadData.setCustomerName(custName.toString());
						List<SegUploadData> data = new ArrayList<SegUploadData>();
						//pcms-query-web查询合同号
						ResponseEntity<JSONObject> responseEntityContractInfo = restTemplate.getForEntity(pcmsQueryUrl + "/service/task/querySignInfo/" + item.getApplyId(), JSONObject.class, map);
						JSONObject signContractInfo = JSONObject.parseObject(responseEntityContractInfo.getBody().toString());
						String contractNo = signContractInfo.getString("contractNo");
						uploadData.setContractNo(contractNo);
						
						uploadData.setCallLetter(item.getGpsId());
						//现地址
						SegUploadData seg1 = new SegUploadData();
						String procVince1 = areaMap.get(tenantVo.getAddrProvince()).replace("省", "");
						String city1 = "";
						if(procVince1.contains("北京") || procVince1.contains("天津") || procVince1.contains("上海") || procVince1.contains("重庆")){
							city1 = "直辖市";
						}else{
							city1 = areaMap.get(tenantVo.getAddrCity());
						}
						//海南 直辖单位
						if(procVince1.equals("海南")&&city1.equals("直辖单位")){
							city1 = "省直辖县级行政单位";
						}
						
						String district1 = areaMap.get(tenantVo.getAddrCounty()).replace("市辖区", "");
						seg1.setProvince(procVince1);
						seg1.setCity(city1);
						seg1.setDistrict(district1);
						//这三procVince1 + city1 + district1个加起来的数据不会超过42位
						String location1 = procVince1 + city1 + district1;
						String location1_ = procVince1 + city1 + district1 + tenantVo.getAddrExt();
						if(location1_.length()>30){//赛格数据限制最长42位
							int distance = 30-location1.length();
							String addrExt = tenantVo.getAddrExt();
							seg1.setAddress(addrExt.substring(0, distance));
						}else{
							seg1.setAddress(tenantVo.getAddrExt());
						}
						seg1.setAddressType("now");
						data.add(seg1);
						//工作地址
						SegUploadData seg2 = new SegUploadData();
						String province2 = areaMap.get(tenantVo.getUnitAddrProvince()).replace("省", "");
						
						String city2 = "";
						if(province2.contains("北京") || province2.contains("天津") || province2.contains("上海") || province2.contains("重庆")){
							city2 = "直辖市";
						}else{
							city2 = areaMap.get(tenantVo.getUnitAddrCity());
						}
						
						if(province2.equals("海南")&&city2.equals("直辖单位")){
							city2 = "省直辖县级行政单位";
						}
						
						String district2 = areaMap.get(tenantVo.getUnitAddrCounty()).replace("市辖区", "");
						seg2.setProvince(province2);
						seg2.setCity(city2);
						seg2.setDistrict(district2);
						String unitAddrExt = tenantVo.getUnitAddrExt();
						String location2 = province2 + city2 + district2;
						String location2_ = province2 + city2 + district2 + unitAddrExt;
						if(location2_.length()>30){
							int distance = 30- location2.length();
							unitAddrExt = unitAddrExt.substring(0, distance);
						}
						seg2.setAddress(unitAddrExt);
						
						seg2.setAddressType("work");
						data.add(seg2);
						uploadData.setData(data);
						//把uploadData转为json
						JSONObject target = (JSONObject)JSON.toJSON(uploadData);
						JSONObject responseReturn = restTemplate.postForObject(segUrl, target, JSONObject.class);
						//判断返回结果
						String returnCode = responseReturn.getString("returnCode");
						if(returnCode.equals(successCode)){
							JSONArray datas = responseReturn.getJSONArray("datas");
							if(datas != null && datas.size() >0){//解决县/区与赛格不匹配问题，直接传入""字符串
								JSONObject data1 = datas.getJSONObject(0);
								String reason = data1.getString("reason");
								if(reason.contains("找不到所属县/区")){
									seg1.setDistrict("");
									seg2.setDistrict("");
									JSONObject target_ = (JSONObject)JSON.toJSON(uploadData);
									responseReturn = restTemplate.postForObject(segUrl, target_, JSONObject.class);
									returnCode = responseReturn.getString("returnCode");
								}
							}
						}
						RecordSegUploadResult record = new RecordSegUploadResult();
						record.setAppId(item.getApplyId());
						record.setGpsNo(item.getGpsId());
						record.setReturnCode(returnCode);
						record.setReturnMsg(responseReturn.getString("returnMsg").toString());
						record.setUploadTimes(1);
						record.setUploadDate(new Date());
						record.setUploadData(target.toString());
						record.setReturnData(responseReturn.toString());
						recordSegUploadResultDao.insert(record);
					}else{
						
					}
				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				RecordSegUploadResult record = new RecordSegUploadResult();
				record.setAppId(item.getApplyId());
				record.setGpsNo(item.getGpsId());
				record.setUploadTimes(1);
				record.setUploadDate(new Date());
				recordSegUploadResultDao.insert(record);
			}
			
		}
	}
	
	

}
