package com.pujjr.gps.service.impl;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pujjr.common.utils.BaseIterableUtils;
import com.pujjr.common.utils.BaseUtils;
import com.pujjr.common.utils.bean.BeanPropertyUtils;
import com.pujjr.gps.api.ApplyParam;
import com.pujjr.gps.api.LockParam;
import com.pujjr.gps.api.StoreBranchParam;
import com.pujjr.gps.api.StorePjParam;
import com.pujjr.gps.dal.dao.GpsApplybackDetailMapper;
import com.pujjr.gps.dal.dao.GpsApplybackMapper;
import com.pujjr.gps.dal.dao.GpsBrandMapper;
import com.pujjr.gps.dal.dao.GpsInstoreHisMapper;
import com.pujjr.gps.dal.dao.GpsInstorePjMapper;
import com.pujjr.gps.dal.dao.GpsLinkmanMapper;
import com.pujjr.gps.dal.dao.GpsLockMapper;
import com.pujjr.gps.dal.dao.GpsReceiveMapper;
import com.pujjr.gps.dal.dao.GpsStocktakeMapper;
import com.pujjr.gps.dal.dao.GpsStoreBranchMapper;
import com.pujjr.gps.dal.dao.GpsStorePjMapper;
import com.pujjr.gps.dal.dao.GpsSupplierMapper;
import com.pujjr.gps.dal.domain.GpsApplyback;
import com.pujjr.gps.dal.domain.GpsApplybackDetail;
import com.pujjr.gps.dal.domain.GpsBrand;
import com.pujjr.gps.dal.domain.GpsInstoreHis;
import com.pujjr.gps.dal.domain.GpsInstorePj;
import com.pujjr.gps.dal.domain.GpsLinkman;
import com.pujjr.gps.dal.domain.GpsLock;
import com.pujjr.gps.dal.domain.GpsReceive;
import com.pujjr.gps.dal.domain.GpsStocktake;
import com.pujjr.gps.dal.domain.GpsStoreBranch;
import com.pujjr.gps.dal.domain.GpsStorePj;
import com.pujjr.gps.dal.domain.GpsSupplier;
import com.pujjr.gps.enumeration.ApplyType;
import com.pujjr.gps.enumeration.GpsStatus;
import com.pujjr.gps.enumeration.GpsType;
import com.pujjr.gps.enumeration.StocktakeType;
import com.pujjr.gps.service.ApplyService;
import com.pujjr.gps.service.GpsService;
import com.pujjr.gps.service.StoreService;
import com.pujjr.gps.service.base.BaseService;
import com.pujjr.gps.util.Utils;
import com.pujjr.gps.vo.GpsBranchVo;
import com.pujjr.gps.vo.GpsInstallHisVo;
import com.pujjr.gps.vo.GpsInstallPjVo;
import com.pujjr.gps.vo.GpsStoreBranchVo;
import com.pujjr.gps.vo.GpsStorePjVo;
import com.pujjr.gps.vo.StoreInfoVo;

import tk.mybatis.mapper.entity.Example;

@Service
public class StoreServiceImpl extends BaseService<StoreService> implements StoreService {
	private static final Logger logger = Logger.getLogger(StoreServiceImpl.class);
	private static final String AUTO_REMARK = "系统自动生成";
	private static final String SYSTEM = "system";
	@Autowired
	private GpsStorePjMapper gpsStorePjMapper;
	@Autowired
	private GpsStoreBranchMapper gpsStoreBranchMapper;
	@Autowired
	private GpsInstorePjMapper gpsInstorePjMapper;
	@Autowired
	private GpsInstoreHisMapper gpsInstoreHisMapper;
	@Autowired
	private GpsLockMapper gpsLockMapper;
	@Autowired
	private GpsBrandMapper GpsBrandMapper;
	@Autowired
	private GpsBrandMapper gpsBrandMapper;
	@Autowired
	private GpsSupplierMapper gpsSupplierMapper;
	@Autowired
	private GpsStocktakeMapper gpsStocktakeMapper;
	@Autowired
	private GpsLinkmanMapper gpsLinkmanMapper;
	@Autowired
	private GpsService gpsService;
	@Autowired
	private ApplyService StoreService;
	@Autowired
	private GpsApplybackDetailMapper gpsApplybackDetailMapper;
	@Autowired
	private GpsReceiveMapper gpsReceiveMapperImpl;
	//excel校验开关
	@Value("${excel.valid.switch}")
	private boolean excelValidSwitch;
	//重复gps编码校验开关
	@Value("${gps.duplicated.switch}")
	private boolean gpsDuplicatedSwitch;
	@Autowired
	private GpsApplybackMapper gpsApplybackMapper;

	@Override
	public PageInfo<GpsStorePjVo> getPjStore(StorePjParam storePjParam) {
		
		// 分页
		Page<GpsStorePj> page = PageHelper.startPage(storePjParam.getPageNum(), storePjParam.getPageSize());
		// 条件查询
		List<GpsStorePj> gpsStorePjList = gpsStorePjMapper.selectStorePjByParams(storePjParam);

		PageInfo<GpsStorePj> pageInfo = new PageInfo<GpsStorePj>(page);

		PageInfo<GpsStorePjVo> pageInfoVo = new PageInfo<GpsStorePjVo>();
		BeanUtils.copyProperties(pageInfo, pageInfoVo);
		List<GpsStorePj> list = pageInfo.getList();
		List<GpsStorePjVo> listVo = new ArrayList<GpsStorePjVo>();
		// 查询所有品牌
		List<GpsBrand> gpsBrandList = gpsBrandMapper.selectAll();
		for (GpsStorePj gpsStorePj : list) {
			GpsStorePjVo gpsStorePjVo = new GpsStorePjVo();
			BeanUtils.copyProperties(gpsStorePj, gpsStorePjVo);
			// 设置品牌名称
			for (GpsBrand gpsBrand : gpsBrandList) {
				if (gpsBrand.getBrandId().equals(gpsStorePjVo.getBrandId())) {
					gpsStorePjVo.setBrandName(gpsBrand.getBrandName());
					break;
				}
			}
			// 设置种类
			if ("WIRE".equals(gpsStorePjVo.getGpsCategory())) {
				gpsStorePjVo.setGpsCategoryName("有线设备");
			}else if("WIRELESS".equals(gpsStorePjVo.getGpsCategory())){
				gpsStorePjVo.setGpsCategoryName("无线设备");
			}else{
				gpsStorePjVo.setGpsCategoryName("");
			}
			// 设置状态
			GpsStatus gpsStatus = GpsStatus.fromValue(gpsStorePjVo.getGpsStatus());
			if (gpsStatus != null) {
				gpsStorePjVo.setGpsStatusName(gpsStatus.getRemark());
			}
			listVo.add(gpsStorePjVo);
		}
		pageInfoVo.setList(listVo);
		return pageInfoVo;
	}

	@Override
	public GpsStorePj savePjStore(GpsInstallPjVo gpsInstallPjVo) {
		List<GpsStorePj> allStorePj = gpsStorePjMapper.selectAll();
		List<GpsStoreBranch> allStoreBranch = gpsStoreBranchMapper.selectAll();
//		boolean isExist = this.isStorePjExist(gpsInstallPjVo, allStore);
		GpsStorePj gpsStore = gpsInstallPjVo.getGpsStorePj();
		if(gpsStore.getStoreId() == null || "".equals(gpsStore.getStoreId())){
			boolean isExist = this.isStoreExist(gpsInstallPjVo.getGpsStorePj().getGpsId(), allStorePj,allStoreBranch);
			if(!isExist){
				// 新增潽金库存
				gpsStore = gpsInstallPjVo.getGpsStorePj();
				String storeId = BaseUtils.get16UUID();
				gpsStore.setStoreId(storeId);
				gpsStore.setOperTime(Calendar.getInstance().getTime());
				gpsStorePjMapper.insertSelective(gpsStore);
				// 新增潽金入库记录
				GpsInstorePj gpsInstorePj = new GpsInstorePj();
				BeanUtils.copyProperties(gpsInstallPjVo, gpsInstorePj);
				gpsInstorePj.setInstoreId(BaseUtils.get16UUID());
				gpsInstorePj.setStoreId(storeId);
				gpsInstorePj.setCreateTime(Calendar.getInstance().getTime());
				gpsInstorePjMapper.insertSelective(gpsInstorePj);
			}
		}else{
			gpsStorePjMapper.updateByPrimaryKeySelective(gpsInstallPjVo.getGpsStorePj());
		}
		return gpsStore;
	}

	@Override
	public GpsStorePj singleAddPjStore(GpsStorePj gpsStore, String createAccountId) {
		List<GpsStorePj> allStorePj = gpsStorePjMapper.selectAll();
		List<GpsStoreBranch> allStoreBranch = gpsStoreBranchMapper.selectAll();
		GpsInstallPjVo gpsInstallPjVo = new GpsInstallPjVo();
		gpsInstallPjVo.setGpsStorePj(gpsStore);
		boolean isExist = this.isStoreExist(gpsInstallPjVo.getGpsStorePj().getGpsId(), allStorePj,allStoreBranch);
		if(!isExist){
			// 新增潽金库存
			String storeId = BaseUtils.get16UUID();
			gpsStore.setStoreId(storeId);
			gpsStore.setOperTime(Calendar.getInstance().getTime());
			gpsStorePjMapper.insertSelective(gpsStore);
			// 新增潽金入库记录
			GpsInstorePj gpsInstorePj = new GpsInstorePj();
			BeanUtils.copyProperties(gpsStore, gpsInstorePj);
			gpsInstorePj.setInstoreId(BaseUtils.get16UUID());
			gpsInstorePj.setStoreId(storeId);
			gpsInstorePj.setCreateAccountId(createAccountId);
			gpsInstorePj.setCreateTime(Calendar.getInstance().getTime());
			gpsInstorePjMapper.insertSelective(gpsInstorePj);
		}else{
			logger.info("跳过当前记录，当前导入潽金库存记录："+JSONObject.toJSONString(gpsInstallPjVo)+"   ，该记录gps编码在潽金库存中已存在。");
		}
		
		return gpsStore;
	}

	@Override
	public List<GpsStorePjVo> batchAddPjStore(MultipartFile file, String consigner) throws Exception {
		
		List<GpsStorePj> allStorePj = gpsStorePjMapper.selectAll();
		List<GpsStoreBranch> allStoreBranch = gpsStoreBranchMapper.selectAll();
		List<GpsStorePjVo> storePjList = new ArrayList();
		// 读取数据
		InputStream is = file.getInputStream();
		XSSFWorkbook workbook = new XSSFWorkbook(is);
		XSSFSheet sheet = workbook.getSheetAt(0);
		int rowTotal = sheet.getPhysicalNumberOfRows();
		int colTotal = 4;
		int rowStart = rowTotal;
		// 所有品牌
		List<GpsBrand> gpsBrandList = GpsBrandMapper.selectAll();
		//潽金待导入库存列表
		List<GpsStorePj> storeList = new ArrayList<GpsStorePj>();
		//潽金库存入库历史列表
		List<GpsInstorePj> storeHisList = new ArrayList<GpsInstorePj>();
		for (int i = 3; i < rowTotal; i++) {
			if(i%1000 == 0){
				logger.info("潽金库存导入，当前行："+i);
			}
			// 当前库存入库记录
			GpsInstallPjVo gpsInstallPjVo = new GpsInstallPjVo();
			// 当前库存记录
			GpsStorePj currStore = new GpsStorePj();
			currStore.setOperTime(Calendar.getInstance().getTime());
			gpsInstallPjVo.setCreateAccountId(consigner);
			gpsInstallPjVo.setCreateTime(Calendar.getInstance().getTime());
			gpsInstallPjVo.setGpsStorePj(currStore);
			XSSFRow row = sheet.getRow(i);
			
			XSSFCell cell2 = row.getCell(2);
			if(cell2 == null)
				continue;
			String gpsId = "";
			if (cell2.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				double d = 0;
				DecimalFormat df = new DecimalFormat("0");
				gpsId = df.format(cell2.getNumericCellValue());
			} else if (cell2.getCellType() == Cell.CELL_TYPE_STRING) {
				gpsId = cell2.getStringCellValue().trim();
			}
			if(gpsId.equals("")){
				continue;
			}
			
			if(excelValidSwitch)
				Utils.validGpsId(gpsId);
			
			String brandName = row.getCell(0).getStringCellValue().trim();
			String gpsCategoryName = row.getCell(1).getStringCellValue().trim();
			String gpsStatusName = "空闲";
			if(row.getCell(3) != null){
				gpsStatusName = row.getCell(3).getStringCellValue().trim();
			}
			currStore.setGpsId(gpsId);

			for (GpsBrand gpsBrand : gpsBrandList) {
				if (gpsBrand.getBrandName().equals(brandName)) {
					currStore.setBrandId(gpsBrand.getBrandId());
					break;
				}
			}

			if ("有线设备".equals(gpsCategoryName)) {
				currStore.setGpsCategory("WIRE");
			} else if("无线设备".equals(gpsCategoryName)){
				currStore.setGpsCategory("WIRELESS");
			}else{
				currStore.setGpsCategory("");
			}
			
			GpsStatus gpsStatus = GpsStatus.fromRemark(gpsStatusName);
			if (gpsStatus != null) {
				currStore.setGpsStatus(gpsStatus.getCode());
			} else {
				Assert.isTrue(gpsStatus != null, "gps状态【"+gpsStatusName+"】错误【合法状态：空闲、锁定、禁用】:"+gpsStatusName);
				new Exception("gps状态错误");
			}
			gpsInstallPjVo.setGpsStorePj(currStore);
			
			gpsInstallPjVo.setGpsId(currStore.getGpsId());
			gpsInstallPjVo.setGpsCategory(currStore.getGpsCategory());
			
			boolean isExist = this.isStoreExist(gpsInstallPjVo.getGpsStorePj().getGpsId(), allStorePj,allStoreBranch);
			//当前gps不存在
			if(!isExist){
				// 调用单笔插入接口
				/*
				this.singleAddPjStore(gpsInstallPjVo);
				*/
				//潽金库存
				String storeId = BaseUtils.get16UUID();
				currStore.setStoreId(storeId);
				storeList.add(currStore);
				
				//潽金库存入库记录
				GpsInstorePj gpsInstallPj = new GpsInstorePj();
				BeanUtils.copyProperties(gpsInstallPjVo, gpsInstallPj);
				gpsInstallPj.setInstoreId(BaseUtils.get16UUID());
				gpsInstallPj.setStoreId(storeId);
				storeHisList.add(gpsInstallPj);
				
				GpsStorePjVo storePjVo = new GpsStorePjVo();
				BeanUtils.copyProperties(currStore, storePjVo);
				storePjList.add(storePjVo);
			}else{
				logger.info("跳过当前记录，当前导入潽金库存记录："+JSONObject.toJSONString(gpsInstallPjVo)+"   ，该记录gps编码在潽金库存中已存在。");
			}
			
		}
		/**
		 * 校验excel是否存在重复gps编码
		 */
		for (int i = 0; i < storePjList.size(); i++) {
			GpsStorePjVo currStore = storePjList.get(i);
			for(int j = i + 1; j < storePjList.size(); j++){
				GpsStorePjVo targetStore = storePjList.get(j);
				Assert.isTrue(!currStore.getGpsId().equals(targetStore.getGpsId()),"存在重复GPS编码【"+currStore.getGpsId()+"】");
			}
		}
		if(storeList.size() > 0)
			gpsStorePjMapper.batchAddStorePj(storeList);
		if(storeHisList.size() > 0)
			gpsStorePjMapper.batchAddInstorePj(storeHisList);
		return storePjList;
	}

	@Override
	public PageInfo<GpsStoreBranchVo> getBaseBranchStore(StoreBranchParam storeBranchParam) {
		if (storeBranchParam != null) {
			String branchId = storeBranchParam.getBranchId();
			/**
			 * 暂时通过经销商编码前4位作为该经销商的1级经销商编码
			 */
			storeBranchParam.setBranchId(branchId.substring(0, 4));
			BeanPropertyUtils.blank2Null(storeBranchParam);
			// 分页
			Page<GpsStoreBranch> page = PageHelper.startPage(storeBranchParam.getPageNum(), storeBranchParam.getPageSize());
			// 查询条件
			Example example = new Example(GpsStoreBranch.class);
			Example.Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(storeBranchParam.getGpsId())) {
				criteria.andLike("gpsId", "%" + storeBranchParam.getGpsId() + "%");
				storeBranchParam.setGpsId(null);
			}
			criteria.andEqualTo("gpsStatus", GpsStatus.STORE_FREE.getCode());
			criteria.andEqualTo(storeBranchParam);
			gpsStoreBranchMapper.selectByExample(example);
			
			//查询所有品牌
			List<GpsBrand> allBrand = gpsBrandMapper.selectAll();
			List<GpsStoreBranch> result = page.getResult();
			List<GpsStoreBranchVo> resultVo = new ArrayList<GpsStoreBranchVo>();
			for (GpsStoreBranch gpsStoreBranch : result) {
				GpsStoreBranchVo gpsStoreBranchVo = new GpsStoreBranchVo();
				BeanUtils.copyProperties(gpsStoreBranch, gpsStoreBranchVo);
				for (GpsBrand gpsBrand : allBrand) {
					if(gpsStoreBranch.getBrandId().equals(gpsBrand.getBrandId())){
						gpsStoreBranchVo.setBrandName(gpsBrand.getBrandName());
						break;
					}
				}
				resultVo.add(gpsStoreBranchVo);
			}
			Page<GpsStoreBranchVo> pageVo = new Page<GpsStoreBranchVo>();
			
			PageInfo<GpsStoreBranch> pageInfo = new PageInfo<GpsStoreBranch>(page);
			PageInfo<GpsStoreBranchVo> pageInfoVo = new PageInfo<GpsStoreBranchVo>();
			
			BeanUtils.copyProperties(pageInfo, pageInfoVo);
			pageInfoVo.setList(resultVo);
			
			return pageInfoVo;
		}
		return null;
	}

	@Override
	public PageInfo<GpsStoreBranchVo> getBranchStore(GpsStoreBranchVo params) {
		// 分页
		Page<GpsStoreBranch> page = PageHelper.startPage(params.getPageNum(), params.getPageSize());
		// 条件查询
		List<GpsStoreBranch> gpsStoreBranchList = gpsStoreBranchMapper.selectStoreBranchByParams(params);
		PageInfo<GpsStoreBranch> pageInfo = new PageInfo<GpsStoreBranch>(page);

		PageInfo<GpsStoreBranchVo> pageInfoVo = new PageInfo<GpsStoreBranchVo>();
		BeanUtils.copyProperties(pageInfo, pageInfoVo);
		List<GpsStoreBranch> list = pageInfo.getList();
		List<GpsStoreBranchVo> listVo = new ArrayList<GpsStoreBranchVo>();
		// 查询所有品牌
		List<GpsBrand> gpsBrandList = gpsBrandMapper.selectAll();
		// 查询所有供应商
		List<GpsSupplier> gpsSupplierList = gpsSupplierMapper.selectAll();
		for (GpsStoreBranch gpsStoreBranch : list) {
			GpsStoreBranchVo gpsStoreBranchVo = new GpsStoreBranchVo();
			BeanUtils.copyProperties(gpsStoreBranch, gpsStoreBranchVo);
			// 设置品牌名称
			for (GpsBrand gpsBrand : gpsBrandList) {
				if (gpsBrand.getBrandId().equals(gpsStoreBranchVo.getBrandId())) {
					gpsStoreBranchVo.setBrandName(gpsBrand.getBrandName());
					break;
				}
			}
			// 设置种类
			if ("WIRE".equals(gpsStoreBranchVo.getGpsCategory())) {
				gpsStoreBranchVo.setGpsCategoryName("有线");
			} else if("WIRELESS".equals(gpsStoreBranchVo.getGpsCategory())) {
				gpsStoreBranchVo.setGpsCategoryName("无线");
			}else{
				gpsStoreBranchVo.setGpsCategoryName("");
			}
			// 设置状态
			GpsStatus gpsStatus = GpsStatus.fromValue(gpsStoreBranchVo.getGpsStatus());
			if (gpsStatus != null) {
				gpsStoreBranchVo.setGpsStatusName(gpsStatus.getRemark());
			}
			// 设置供应商名称
			for (GpsSupplier gpsSupplier : gpsSupplierList) {
				if (gpsSupplier.getSupplierId().equals(gpsStoreBranchVo.getSupplierId())) {
					gpsStoreBranchVo.setSupplierName(gpsSupplier.getSupplierName());
				}
			}
			// 设置经销商名称
			List<GpsBranchVo> branchList = gpsService.getAllBranch();
			for (GpsBranchVo gpsBranchVo : branchList) {
				if (gpsBranchVo.getBranchId().equals(gpsStoreBranchVo.getBranchId())) {
					gpsBranchVo.setBranchName(gpsStoreBranchVo.getBranchName());
					break;
				}
			}
			listVo.add(gpsStoreBranchVo);
		}
		pageInfoVo.setList(listVo);
		return pageInfoVo;
	}

	@Override
	public GpsStoreBranch saveBranchStore(GpsInstallHisVo gpsInstallHisVo) {
		// 记录经销商库存
		String storeId = BaseUtils.get16UUID();
		GpsStoreBranch gpsStoreBranch = gpsInstallHisVo.getGpsStoreBranch();
		// 校验gps编码是否存在
		List<GpsStorePj> allStorePj = gpsStorePjMapper.selectAll();
		List<GpsStoreBranch> allStoreBranch = gpsStoreBranchMapper.selectAll();
		if(gpsStoreBranch.getStoreId()  == null || "".equals(gpsStoreBranch.getStoreId())){
			boolean isExist = this.isStoreExist(gpsInstallHisVo.getGpsStoreBranch().getGpsId(), allStorePj,allStoreBranch);
			Assert.isTrue(!isExist,"潽金库存或经销商库存已存在gps编码【"+gpsInstallHisVo.getGpsStoreBranch().getGpsId()+"】"); 
			
			gpsStoreBranch.setStoreId(storeId);
			gpsStoreBranch.setOperTime(Calendar.getInstance().getTime());
			gpsStoreBranchMapper.insertSelective(gpsStoreBranch);
			// 记录入库记录
			GpsInstoreHis gpsInstoreHis = new GpsInstoreHis();
			gpsInstoreHis.setInstoreHisId(BaseUtils.get16UUID());
			gpsInstoreHis.setCreateTime(Calendar.getInstance().getTime());
			gpsInstoreHis.setStoreId(storeId);
			gpsInstoreHis.setCreateAccountId(gpsInstallHisVo.getCreateAccountId());

			GpsReceive gpsReceive = gpsInstallHisVo.getGpsReceive();
			if (gpsReceive != null) {
				gpsInstoreHis.setReceiveId(gpsReceive.getReceiveId());
			}
			gpsInstoreHisMapper.insertSelective(gpsInstoreHis);
		}else{
			gpsStoreBranchMapper.updateByPrimaryKeySelective(gpsStoreBranch);
		}
		return gpsInstallHisVo.getGpsStoreBranch();
	}

	@Override
	public List<GpsStoreBranchVo> batchAddBranchStore(MultipartFile file, String consigner) throws Exception {
		List<GpsStoreBranch> allStore = gpsStoreBranchMapper.selectAll();
		List<GpsStoreBranchVo> storeBranchList = new ArrayList();
		// 读取数据
		InputStream is = file.getInputStream();
		XSSFWorkbook workbook = new XSSFWorkbook(is);
		XSSFSheet sheet = workbook.getSheetAt(0);
		int rowTotal = sheet.getPhysicalNumberOfRows();
		int colTotal = 6;
		int rowStart = rowTotal;
		// 所有品牌
		List<GpsBrand> gpsBrandList = GpsBrandMapper.selectAll();
		// 待导入库存列表
		List<GpsStoreBranch> storeList = new ArrayList<GpsStoreBranch>();
		//入库历史列表
		List<GpsInstoreHis> storeHisList = new ArrayList<GpsInstoreHis>();
		// 所有供应商
		List<GpsSupplier> gpsSupplierList = gpsSupplierMapper.selectAll();
		// 所有经销商
		List<GpsBranchVo> branchList = gpsService.getAllBranch();
		//所有潽金库存
		List<GpsStorePj> allStorePj = gpsStorePjMapper.selectAll();
		//所有经销商库存
		List<GpsStoreBranch> allStoreBranch = gpsStoreBranchMapper.selectAll();
		for (int i = 3; i < rowTotal; i++) {
			logger.info("******************"+i+"行*******************");
			// 当前库存入库记录
			GpsInstallHisVo gpsInstallHisVo = new GpsInstallHisVo();
			// 当前库存记录
			GpsStoreBranch currStore = new GpsStoreBranch();
			gpsInstallHisVo.setCreateAccountId(consigner);
			gpsInstallHisVo.setCreateTime(Calendar.getInstance().getTime());
			gpsInstallHisVo.setGpsStoreBranch(currStore);
			XSSFRow row = sheet.getRow(i);
			if(row == null)
				continue;
			String branchName = row.getCell(0) == null ? "" : row.getCell(0).getStringCellValue().trim();
			if("".equals(branchName))
				continue;
			String gpsSupplierName = row.getCell(1) == null ? "" : row.getCell(1).getStringCellValue().trim();
			Assert.isTrue(!"".equals(gpsSupplierName), "当前经销商【"+branchName+"】供应商名称为必填项！");
			String brandName = row.getCell(2) == null ? "" : row.getCell(2).getStringCellValue().trim();
			Assert.isTrue(!"".equals(brandName), "当前经销商【"+branchName+"】品牌名称为必填项！");
			String gpsCategoryName = row.getCell(3) == null ? "" : row.getCell(3).getStringCellValue().trim();
			Assert.isTrue(!"".equals(gpsCategoryName), "当前经销商【"+branchName+"】GPS种类为必填项！");
			Cell cell4 = row.getCell(4);
			String gpsId = "";
			Assert.isTrue(!(cell4 == null), "当前经销商【"+branchName+"】GPS编码为必填项！");
			if (cell4.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				DecimalFormat df = new DecimalFormat("0");
				gpsId = df.format(cell4.getNumericCellValue());
			} else if (cell4.getCellType() == Cell.CELL_TYPE_STRING) {
				gpsId = cell4.getStringCellValue().trim();
			}
			/**
			 * gps编码合法性校验
			 * 注意：现有信贷系统中，gps编码存在重复的情况，在导入“库存信息导入-经销商信贷系统GPSv1.0.xlsx”时，不做excel gps编码合法性检测
			 * 导入“2、库存信息导入-经销商v1.0.xlsx”时，需要校验excel文件中gps编码合法性
			 */
			if(excelValidSwitch)
				Utils.validGpsId(gpsId);
			String gpsStatusName = row.getCell(5) == null ? "" : row.getCell(5).getStringCellValue().trim();
//			Assert.isTrue(!"".equals(gpsStatusName), "当前经销商【"+branchName+"】GPS状态为必填项！");
			String applyId = "";
			try {
				applyId = row.getCell(6).getStringCellValue().trim() + "";
			} catch (Exception e) {
			}
			if ("".equals(gpsStatusName.trim())) {
				gpsStatusName = "空闲";
			}
			//操作时间（主要针对信贷系统中已存在gps）
			Cell operTimeCell = row.getCell(7);
			Date operTime = null;
			if(operTimeCell != null){
				String operTimeStr = operTimeCell.getStringCellValue();
				operTime = Utils.formateString2Date(operTimeStr, "yyyy/MM/dd HH:mm");
			}
			currStore.setOperTime(operTime);
			currStore.setGpsId(gpsId);
			currStore.setApplyId(applyId);
			// 设置品牌
			for (GpsBrand gpsBrand : gpsBrandList) {
				if (gpsBrand.getBrandName().equals(brandName)) {
					currStore.setBrandId(gpsBrand.getBrandId());
					break;
				}
			}

			// 设置设备类型
			if(gpsCategoryName.length() > 0){
				if ("有线设备".equals(gpsCategoryName)) {
					currStore.setGpsCategory("WIRE");
				} else if("无线设备".equals(gpsCategoryName)){
					currStore.setGpsCategory("WIRELESS");
				}else{
					Assert.isTrue(false, "当前经销商【"+branchName+"】设备类型错误，设备类型合法输入项：有线设备、无线设备！");
				}
			}else{
				currStore.setGpsCategory("");
			}
			
			// 设置gps状态
			GpsStatus gpsStatus = GpsStatus.fromRemark(gpsStatusName);
			if (gpsStatus != null) {
				currStore.setGpsStatus(gpsStatus.getCode());
			} else {
				new Exception("gps状态错误");
			}

			// 设置供应商编号
			for (GpsSupplier gpsSupplier : gpsSupplierList) {
				if (gpsSupplier.getSupplierName().equals(gpsSupplierName)) {
					currStore.setSupplierId(gpsSupplier.getSupplierId());
				}
			}

			// 设置经销商编号（信贷系统查询的所有经销商）
			boolean isFindBranch = false;
			for (GpsBranchVo gpsBranchVo : branchList) {
				if (gpsBranchVo.getBranchName().equals(branchName)) {
					currStore.setBranchId(gpsBranchVo.getBranchId());
					isFindBranch = true;
//					logger.info("找到：" + branchName);
					break;
				}
			}
			Assert.isTrue(isFindBranch, "未找到对应经销商名称：" + branchName + "|" + gpsSupplierName + "|" + brandName + "|" + gpsCategoryName + "|" + gpsId);
			currStore.setBranchName(branchName);
			
			//校验在潽金库存是否存在
			boolean isExistInPj = this.isStoreExist(gpsInstallHisVo.getGpsStoreBranch().getGpsId(), allStorePj, allStoreBranch);
			//校验经销商库存是否存在此gps
			GpsStoreBranch existStoreBranch = this.isStoreBranchExist(gpsInstallHisVo, allStoreBranch);
//			boolean isExist = this.isStoreExist(gpsInstallHisVo.getGpsStoreBranch().getGpsId(), allStorePj,allStoreBranch);
			//当前添加GPS不存在与经销商库存
			if(existStoreBranch == null){
				if(isExistInPj)
					logger.info("潽金库存已存在gps编码【"+gpsInstallHisVo.getGpsStoreBranch().getGpsId()+"】");
				Assert.isTrue(!isExistInPj,"潽金库存已存在gps编码【"+gpsInstallHisVo.getGpsStoreBranch().getGpsId()+"】");
				
				// 调用单笔插入接口(改为批量接口：2017-12-30)
				/*
				this.singleAddBranchStore(gpsInstallHisVo);
				*/
				
				//当前导入经销商库存记录结合
				String currStoreId = BaseUtils.get16UUID();
				currStore.setStoreId(currStoreId);
				storeList.add(currStore);
				//入库历史集合
				GpsInstoreHis gpsInstallHis = new GpsInstoreHis();
				BeanUtils.copyProperties(gpsInstallHisVo, gpsInstallHis);
				gpsInstallHis.setStoreId(currStoreId);
				gpsInstallHis.setInstoreHisId(BaseUtils.get16UUID());
				storeHisList.add(gpsInstallHis);
				
				GpsStoreBranchVo storeBranchVo = new GpsStoreBranchVo();
				BeanUtils.copyProperties(currStore, storeBranchVo);
				storeBranchList.add(storeBranchVo);
			}else{
				//存在,更新状态。
				existStoreBranch.setGpsStatus(gpsStatus.getCode());
				if(operTime != null)
					existStoreBranch.setOperTime(operTime);
				else{
//					Assert.isTrue(false, JSONObject.toJSONString(existStoreBranch));
				}
				gpsStoreBranchMapper.updateByPrimaryKeySelective(existStoreBranch);
				logger.info("【库存状态更新】记录已存在于经销商库存中："+JSONObject.toJSONString(existStoreBranch)+"   ,更改gps状态为："+gpsStatus.getCode()+"|"+gpsStatusName);
			}
		}
		
		/**
		 * 校验excel是否存在重复gps编码
		 * 注意：现有信贷系统中，gps编码存在重复的情况，在导入“库存信息导入-经销商信贷系统GPSv1.0.xlsx”时，不做excel gps编码重复检测
		 * 导入“2、库存信息导入-经销商v1.0.xlsx”时，需要校验excel文件中gps编码重复性
		 */
		if(excelValidSwitch){
			for (int i = 0; i < storeList.size(); i++) {
				GpsStoreBranch currStore = storeList.get(i);
				for(int j = i + 1; j < storeList.size(); j++){
					GpsStoreBranch targetStore = storeList.get(j);
					Assert.isTrue(!currStore.getGpsId().equals(targetStore.getGpsId()),"导入失败！存在重复GPS编码【"+currStore.getGpsId()+"】");
				}
			}
		}
		//调用批量插入接口
		int bufSize = 5000;//缓存大小
		
		if(storeList.size() > 0){
			int loopTime = storeList.size()%bufSize == 0 ? storeList.size()/bufSize : storeList.size()/bufSize+1;
			for (int i = 0; i < loopTime; i++) {
				List<GpsStoreBranch> loopStoreList = new ArrayList<GpsStoreBranch>();
				for (int j = i * bufSize; j < (i + 1) * bufSize; j++) {
					if(j < storeList.size()){
						loopStoreList.add(storeList.get(j));
					}else{
						break;
					}
				}
				logger.info("导入经销商库存，批次："+i);
				gpsStoreBranchMapper.batchAddBranchStore(loopStoreList);
			}
		}
			
		if(storeHisList.size() > 0){
			int loopTime = storeHisList.size()%bufSize == 0 ? storeHisList.size()/bufSize : storeHisList.size()/bufSize+1;
			for (int i = 0; i < loopTime; i++) {
				List<GpsInstoreHis> loopStoreHisList = new ArrayList<GpsInstoreHis>();
				for (int j = i * bufSize; j < (i + 1) * bufSize; j++) {
					if(j < storeHisList.size()){
						loopStoreHisList.add(storeHisList.get(j));
					}else{
						break;
					}
				}
//				logger.info("导入经销商库存插入历史，批次："+i);
				gpsStoreBranchMapper.batchAddGpsInstallHis(loopStoreHisList);
			}
		}
			
		
		return storeBranchList;
	}

	@Override
	public GpsStoreBranch disabelStoreBranch(String gpsId) {
		/**
		 * 当前已处于退货流程中(处于未提交、退货中的退货申请)的GPS禁止执行禁用/启用
		 */
		List<GpsApplybackDetail> allApplybackDetail = new ArrayList<GpsApplybackDetail>();
		List<GpsApplyback> gpsApplybackList = gpsApplybackMapper.selectNotFinishApplyback();
		for (GpsApplyback gpsApplyback : gpsApplybackList) {
			String applybackId = gpsApplyback.getApplybackId();
			List<GpsApplybackDetail> tempGpsApplybackDetailList = gpsApplybackDetailMapper.selectApplybackDetailByApplybackId(applybackId);
			allApplybackDetail.addAll(tempGpsApplybackDetailList);
		}
		for (GpsApplybackDetail gpsApplybackDetail : allApplybackDetail) {
			Assert.isTrue(!gpsApplybackDetail.getGpsId().equals(gpsId), "当前GPS【"+gpsId+"】已发起退货申请，无法执行【禁用/启用】");
		}
		
		GpsStoreBranch gpsStoreBranch = gpsStoreBranchMapper.selectStoreBranchByGpsId(gpsId);
		String currGpsStatus = gpsStoreBranch.getGpsStatus();
		/**
		 * 已禁用的gps设置为：空闲 空闲的gps设置为：禁用
		 */
		if (currGpsStatus.equals(GpsStatus.STORE_FREE.getCode())) {
			// 空闲的gps设置为：禁用
			gpsStoreBranch.setGpsStatus(GpsStatus.STORE_DISABLE.getCode());
		} else if (currGpsStatus.equals(GpsStatus.STORE_DISABLE.getCode())) {
			// 已禁用的gps设置为：启用
			gpsStoreBranch.setGpsStatus(GpsStatus.STORE_FREE.getCode());
		}
		Assert.isTrue(!currGpsStatus.equals(GpsStatus.STORE_INSTALLED.getCode()), "【禁用/启用】无效，当前GPS【"+gpsId+"】已安装");
		Assert.isTrue(!currGpsStatus.equals(GpsStatus.STORE_LOCK.getCode()), "【禁用/启用】无效，当前GPS【"+gpsId+"】已锁定");
		// updateByPrimaryKey2
		gpsStoreBranchMapper.updateByPrimaryKey(gpsStoreBranch);
		return gpsStoreBranch;
	}
	
	@Override
	public GpsStorePj disabelStorePj(String gpsId) {
		GpsStorePj gpsStorePj = gpsStorePjMapper.selectStorePjByGpsId(gpsId);
		String currGpsStatus = gpsStorePj.getGpsStatus();
		/**
		 * 已禁用的gps设置为：空闲 空闲的gps设置为：禁用
		 */if (currGpsStatus.equals(GpsStatus.STORE_FREE.getCode())) {
			// 空闲的gps设置为：禁用
			gpsStorePj.setGpsStatus(GpsStatus.STORE_DISABLE.getCode());
		} else if (currGpsStatus.equals(GpsStatus.STORE_DISABLE.getCode())) {
			// 已禁用的gps设置为：启用
			gpsStorePj.setGpsStatus(GpsStatus.STORE_FREE.getCode());
		}
		Assert.isTrue(!currGpsStatus.equals(GpsStatus.STORE_INSTALLED.getCode()), "【禁用/启用】无效，当前GPS【"+gpsId+"】已安装");
		Assert.isTrue(!currGpsStatus.equals(GpsStatus.STORE_LOCK.getCode()), "【禁用/启用】无效，当前GPS【"+gpsId+"】已锁定");
		// updateByPrimaryKey2
		gpsStorePjMapper.updateByPrimaryKey(gpsStorePj);
		return gpsStorePj;
	}

	@Override
	public int delete(String gpsId) {
		Assert.isTrue(StringUtils.isNotBlank(gpsId), "GPS编号不能为空");
		GpsStoreBranch gpsStoreBranch = gpsStoreBranchMapper.selectByGpsId(gpsId);
		int count = 0;
		if (gpsStoreBranch != null) {
			Assert.isTrue(!StringUtils.equals(gpsStoreBranch.getGpsStatus(), GpsStatus.STORE_INSTALLED.getCode()), "不能删除已出库的GPS[" + gpsId + "]");
			gpsLockMapper.deleteByPrimaryKey(gpsId);
			count = gpsStoreBranchMapper.deleteByGpsId(gpsId);
		}
		return count;
	}

	@Override
	public GpsStoreBranch lock(String gpsId, String appId) {
		Assert.isTrue(StringUtils.isNotBlank(appId), "申请单编号不能为空");
		GpsStoreBranch gpsStoreBranch = getGps(gpsId);
		String gpsType = GpsType.valueOf(gpsStoreBranch.getGpsCategory()).getRemark();
		// GPS不空闲
		if (!StringUtils.equals(gpsStoreBranch.getGpsStatus(), GpsStatus.STORE_FREE.getCode())) {
			Assert.isTrue(StringUtils.equals(gpsStoreBranch.getApplyId(), appId), "该" + gpsType + "GPS[" + gpsId + "]已被订单" + gpsStoreBranch.getApplyId() + "占用");
			return gpsStoreBranch;
		}
		logger.info("锁定GPS:" + gpsId);
		gpsStoreBranch.setGpsStatus(GpsStatus.STORE_LOCK.getCode());
		gpsStoreBranch.setApplyId(appId);
		gpsStoreBranchMapper.updateByPrimaryKeySelective(gpsStoreBranch);
		GpsLock gpsLock = new GpsLock();
		gpsLock.setGpsId(gpsId);
		gpsLock.setApplyId(appId);
		gpsLock.setLockTime(new Date());
		gpsLockMapper.insert(gpsLock);
		return gpsStoreBranch;
	}

	@Override
	public GpsStoreBranch unlock(String gpsId) {
		GpsStoreBranch gpsStoreBranch = getGps(gpsId);
//		Assert.isTrue(!StringUtils.equals(gpsStoreBranch.getGpsStatus(), GpsStatus.STORE_INSTALLED.getCode()), "不能解锁已出库的GPS[" + gpsId + "]");
		if (StringUtils.equals(gpsStoreBranch.getGpsStatus(), GpsStatus.STORE_LOCK.getCode())) {
			logger.info("解锁GPS:" + gpsId);
			gpsStoreBranch.setGpsStatus(GpsStatus.STORE_FREE.getCode());
			gpsStoreBranch.setApplyId("");
			gpsStoreBranchMapper.updateByPrimaryKeySelective(gpsStoreBranch);
		}
		gpsLockMapper.deleteByPrimaryKey(gpsId);
		return gpsStoreBranch;
	}
	
	@Override
	public GpsStorePj unlockPj(String gpsId) {
//		GpsStoreBranch gpsStoreBranch = getGps(gpsId);
		GpsStorePj gpsStorePj = gpsStorePjMapper.selectStorePjByGpsId(gpsId);
		gpsStorePj.setGpsStatus(GpsStatus.STORE_FREE.getCode());
		gpsStorePjMapper.updateByPrimaryKeySelective(gpsStorePj);
		return gpsStorePj;
	}

	@Override
	public GpsStoreBranch recover(String gpsId) {
		GpsStoreBranch gpsStoreBranch = getGps(gpsId);
		Assert.isTrue(!StringUtils.equals(gpsStoreBranch.getGpsStatus(), GpsStatus.STORE_LOCK.getCode()), "不能恢复锁定的GPS[" + gpsId + "]");
		if (StringUtils.equals(gpsStoreBranch.getGpsStatus(), GpsStatus.STORE_INSTALLED.getCode())) {
			logger.info("恢复GPS:" + gpsId);
			gpsStoreBranch.setGpsStatus(GpsStatus.STORE_FREE.getCode());
			gpsStoreBranchMapper.updateByPrimaryKeySelective(gpsStoreBranch);
		}
		gpsLockMapper.deleteByPrimaryKey(gpsId);
		return gpsStoreBranch;
	}

	@Override
	public GpsStoreBranch outStore(String gpsId) {
		GpsStoreBranch gpsStoreBranch = getGps(gpsId);
		// Assert.isTrue(StringUtils.equals(gpsStoreBranch.getGpsStatus(), GpsStatus.STORE_LOCK.getCode()), "只能出货GPS[" + gpsId + "]状态为锁定的GPS");
		logger.info("出货GPS:" + gpsId);
		gpsStoreBranch.setGpsStatus(GpsStatus.STORE_INSTALLED.getCode());
		gpsStoreBranch.setOperTime(new Date());
		gpsStoreBranchMapper.updateByPrimaryKeySelective(gpsStoreBranch);
		return gpsStoreBranch;
	}

	@Override
	public List<GpsStoreBranch> batchLock(LockParam lockParm) {
		String appId = lockParm.getAppId();
		Assert.isTrue(StringUtils.isNotBlank(appId), "申请单号表不能为空");
		List<GpsStoreBranch> gpsStoreBranchList = new ArrayList<>();
		logger.info("申请单锁定批处理:" + appId);
		// 批量解锁
		if (BaseIterableUtils.isNotEmpty(lockParm.getUnlockGpsIdList())) {
			for (String gpsId : lockParm.getUnlockGpsIdList()) {
				GpsStoreBranch gpsStoreBranch = unlock(gpsId);
				gpsStoreBranchList.add(gpsStoreBranch);
			}
		}
		// 批量锁定
		if (BaseIterableUtils.isNotEmpty(lockParm.getLockGpsIdList())) {
			for (String gpsId : lockParm.getLockGpsIdList()) {
				GpsStoreBranch gpsStoreBranch = lock(gpsId, appId);
				gpsStoreBranchList.add(gpsStoreBranch);
			}
		}
		// 批量出库
		if (BaseIterableUtils.isNotEmpty(lockParm.getOutStoreGpsIdList())) {
			for (String gpsId : lockParm.getOutStoreGpsIdList()) {
				GpsStoreBranch gpsStoreBranch = outStore(gpsId);
				gpsStoreBranchList.add(gpsStoreBranch);
			}
		}
		// 批量恢复
		if (BaseIterableUtils.isNotEmpty(lockParm.getRecoverGpsList())) {
			for (String gpsId : lockParm.getRecoverGpsList()) {
				GpsStoreBranch gpsStoreBranch = recover(gpsId);
				gpsStoreBranchList.add(gpsStoreBranch);
			}
		}
		return gpsStoreBranchList;
	}

	/**
	 * @param gpsId
	 * @param appId
	 * @return
	 */
	private GpsStoreBranch getGps(String gpsId) {
		Assert.isTrue(StringUtils.isNotBlank(gpsId), "GPS编号不能为空");
		GpsStoreBranch gpsStoreBranch = gpsStoreBranchMapper.selectByGpsId(gpsId);
		Assert.notNull(gpsStoreBranch, "未找到GPS编号【"+gpsId+"】对应的GPS库存");
		return gpsStoreBranch;
	}

	/**
	 * 盘点单个经销商
	 * 
	 * @param branchId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private GpsStocktake checkStore(String branchId, Date beginDate, Date endDate) {
		Assert.isTrue(StringUtils.isNotBlank(branchId), "经销商编号不能为空");
		GpsStocktake gpsStocktake = new GpsStocktake();
		gpsStocktake.setBranchId(branchId);
		logger.info("被盘点经销商ID:" + branchId);
		// 已用
		String installedCode = GpsStatus.STORE_INSTALLED.getCode();
		gpsStocktake.setUsedWireNum(gpsStoreBranchMapper.countBranchStoreGps(branchId, installedCode, GpsType.WIRE.name(), beginDate, endDate));
		gpsStocktake.setUsedWirelessNum(gpsStoreBranchMapper.countBranchStoreGps(branchId, installedCode, GpsType.WIRELESS.name(), beginDate, endDate));
		// 可用
		String freeCode = GpsStatus.STORE_FREE.getCode();
		gpsStocktake.setTotalWireNum(gpsStoreBranchMapper.countBranchStoreGps(branchId, freeCode, GpsType.WIRE.name(), null, null));
		gpsStocktake.setTotalWirelessNum(gpsStoreBranchMapper.countBranchStoreGps(branchId, freeCode, GpsType.WIRELESS.name(), null, null));

		// 默认申请量 本月使用量*1.5-当前库存量
		Double applyWireNum = gpsStocktake.getUsedWireNum() * 1.5 - gpsStocktake.getTotalWireNum();
		Double applyWirelessNum = gpsStocktake.getUsedWirelessNum() * 1.5 - gpsStocktake.getTotalWirelessNum();
		gpsStocktake.setApplyWireNum(applyWireNum.intValue());
		gpsStocktake.setApplyWirelessNum(applyWirelessNum.intValue());

		logger.info("盘点结果" + JSON.toJSONString(gpsStocktake));
		return gpsStocktake;
	}

	@Override
	public GpsStocktake tempCheckBranchStore(String branchId) {
		Date beginDate = DateUtils.truncate(new Date(), Calendar.MONTH);
		return checkStore(branchId, beginDate, null);
	}

	@Override
	public int checkAllBranchStore() {
		int count = 0;
		// 批量盘点上一月
		Date nowDate = DateUtils.addMonths(new Date(), -1);
		Date beginDate = DateUtils.truncate(nowDate, Calendar.MONTH);
		Date endDate = DateUtils.ceiling(nowDate, Calendar.MONTH);
		// 获取全部经销商列表
		List<GpsBranchVo> gpsBranchList = gpsService.getAllBranch();
		if (BaseIterableUtils.isNotEmpty(gpsBranchList)) {
			for (GpsBranchVo gpsBranchVo : gpsBranchList) {
				logger.info("被盘点经销商:" + gpsBranchVo.getBranchName());
				logger.info("是否在线:" + gpsBranchVo.getEnabled());
				// 判断是否在线
				if (Boolean.parseBoolean(gpsBranchVo.getEnabled())) {
					String branchId = gpsBranchVo.getBranchId();
					// 盘点
					GpsStocktake gpsStocktake = checkStore(branchId, beginDate, endDate);
					if ((gpsStocktake.getApplyWireNum() + gpsStocktake.getApplyWirelessNum()) > 0) {
						// 盘点记录
						gpsStocktake.setStocktakeId(BaseUtils.get16UUID());
						gpsStocktake.setBranchId(branchId);
						gpsStocktake.setBranchName(gpsBranchVo.getBranchName());
						gpsStocktake.setCreateTime(new Date());
						gpsStocktake.setStocktakeType(StocktakeType.BATCH.getCode());
						gpsStocktakeMapper.insert(gpsStocktake);
						// 自动生成申请单
						ApplyParam applyParam = new ApplyParam();
						applyParam.setStocktakeId(gpsStocktake.getStocktakeId());
						applyParam.setApplyType(ApplyType.AUTO.name());
						applyParam.setBranchId(branchId);
						applyParam.setBranchName(gpsBranchVo.getBranchName());
						applyParam.setCreateAccountId(SYSTEM);
						applyParam.setRemark(AUTO_REMARK);
						applyParam.setWireNum(gpsStocktake.getApplyWireNum());
						applyParam.setWirelessNum(gpsStocktake.getApplyWirelessNum());
						// 获取联系人默认地址
						GpsLinkman gpsLinkman = gpsLinkmanMapper.selectDefaultLinkmanByBranchId(branchId);
						if (gpsLinkman != null) {
							applyParam.setLinkmanId(gpsLinkman.getLinkmanId());
						}
						StoreService.tempSaveGpsApply(applyParam,StocktakeType.BATCH);
						// 计数
						count++;
					}
				}
			}
		}
		logger.info(MessageFormat.format("以盘点经销商{0}家,自动发起申请{1}笔", gpsBranchList.size(), count));
		return count;
	}

	@Override
	public StoreInfoVo getStoreInfo(GpsStoreBranchVo gpsStoreBranchVo) {
		StoreInfoVo storeInfoVo = new StoreInfoVo();
		// 空闲总量
		int totalFree = 0;
		// 安装总量
		int totalUse = 0;
		// 锁定总量
		int totalLock = 0;
		// 禁用总量
		int totalForbidden = 0;

//		GpsStoreBranchVo gpsStoreBranchVo = new GpsStoreBranchVo();
//		gpsStoreBranchVo.setBranchId(branchId);
		List<GpsStoreBranch> storeList = gpsStoreBranchMapper.selectStoreBranchByParams(gpsStoreBranchVo);
		for (GpsStoreBranch gpsStoreBranch : storeList) {
			switch (gpsStoreBranch.getGpsStatus()) {
			case "200":// 空闲
				totalFree++;
				break;
			case "201":// 锁定
				totalLock++;
				break;
			case "202":// 已安装
				totalUse++;
				break;
			case "203":// 禁用
				totalForbidden++;
				break;

			}
		}
		storeInfoVo.setTotalFree(totalFree);
		storeInfoVo.setTotalLock(totalLock);
		storeInfoVo.setTotalUse(totalUse);
		storeInfoVo.setTotalForbidden(totalForbidden);
		return storeInfoVo;
	}

	@Override
	public boolean isStorePjExist(GpsInstallPjVo gpsInstallPjVo,List<GpsStorePj> allStore) {
		boolean isExist = false;
		for (GpsStorePj currGpsStorePj : allStore) {
			if(currGpsStorePj.getGpsId().equals(gpsInstallPjVo.getGpsId()) || currGpsStorePj.getGpsId().equals(gpsInstallPjVo.getGpsStorePj().getGpsId())){
				isExist = true;
//				Assert.isTrue(!currGpsStorePj.getGpsId().equals(gpsInstallPjVo.getGpsId()), "新增失败，GPS编码 " + gpsInstallPjVo.getGpsId() + " 对应GPS存在");
				break;
			}
		}
		if(gpsDuplicatedSwitch){
			Assert.isTrue(!(isExist), "潽金库存已存在此gps编码："+gpsInstallPjVo.getGpsStorePj().getGpsId());
		}
		return isExist;
	}

	@Override
	public GpsStoreBranch isStoreBranchExist(GpsInstallHisVo gpsInstallHisVo,List<GpsStoreBranch> allStore) {
		boolean isExist = false;
		GpsStoreBranch gpsStoreBranch = null;
		for (GpsStoreBranch currGpsStoreBranch : allStore) {
			if(currGpsStoreBranch.getGpsId().equals(gpsInstallHisVo.getGpsStoreBranch().getGpsId())){
				isExist = true;
				gpsStoreBranch = currGpsStoreBranch;
//				Assert.isTrue(!currGpsStoreBranch.getGpsId().equals(gpsInstallHisVo.getGpsStoreBranch().getGpsId()), "新增失败，GPS编码 " + currGpsStoreBranch.getGpsId() + " 对应GPS存在");
				break;
			}
		}
		if(gpsDuplicatedSwitch){
			Assert.isTrue(!(isExist), "经销商库存已存在此gps编码："+gpsInstallHisVo.getGpsStoreBranch().getGpsId());
		}
		return gpsStoreBranch;
	}

	@Override
	public List<GpsStorePjVo> getAllPjStore(StorePjParam storePjParam) {
		// 条件查询
		List<GpsStorePj> gpsStorePjList = gpsStorePjMapper.selectStorePjByParams(storePjParam);
		List<GpsStorePjVo> listVo = new ArrayList<GpsStorePjVo>();
		// 查询所有品牌
		List<GpsBrand> gpsBrandList = gpsBrandMapper.selectAll();
		for (GpsStorePj gpsStorePj : gpsStorePjList) {
			GpsStorePjVo gpsStorePjVo = new GpsStorePjVo();
			BeanUtils.copyProperties(gpsStorePj, gpsStorePjVo);
			// 设置品牌名称
			for (GpsBrand gpsBrand : gpsBrandList) {
				if (gpsBrand.getBrandId().equals(gpsStorePjVo.getBrandId())) {
					gpsStorePjVo.setBrandName(gpsBrand.getBrandName());
					break;
				}
			}
			// 设置种类
			if ("WIRE".equals(gpsStorePjVo.getGpsCategory())) {
				gpsStorePjVo.setGpsCategoryName("有线设备");
			}else if("WIRELESS".equals(gpsStorePjVo.getGpsCategory())){
				gpsStorePjVo.setGpsCategoryName("无线设备");
			}else{
				gpsStorePjVo.setGpsCategoryName("");
			}
			// 设置状态
			GpsStatus gpsStatus = GpsStatus.fromValue(gpsStorePjVo.getGpsStatus());
			if (gpsStatus != null) {
				gpsStorePjVo.setGpsStatusName(gpsStatus.getRemark());
			}
			listVo.add(gpsStorePjVo);
		}
		return listVo;
	}

	@Override
	public List<GpsStoreBranchVo> getAllBranchStore(GpsStoreBranchVo params) {
		// 条件查询
		List<GpsStoreBranch> gpsStoreBranchList = gpsStoreBranchMapper.selectStoreBranchByParams(params);
		List<GpsStoreBranchVo> listVo = new ArrayList<GpsStoreBranchVo>();
		// 查询所有品牌
		List<GpsBrand> gpsBrandList = gpsBrandMapper.selectAll();
		// 查询所有供应商
		List<GpsSupplier> gpsSupplierList = gpsSupplierMapper.selectAll();
		for (GpsStoreBranch gpsStoreBranch : gpsStoreBranchList) {
			GpsStoreBranchVo gpsStoreBranchVo = new GpsStoreBranchVo();
			BeanUtils.copyProperties(gpsStoreBranch, gpsStoreBranchVo);
			// 设置品牌名称
			for (GpsBrand gpsBrand : gpsBrandList) {
				if (gpsBrand.getBrandId().equals(gpsStoreBranchVo.getBrandId())) {
					gpsStoreBranchVo.setBrandName(gpsBrand.getBrandName());
					break;
				}
			}
			// 设置种类
			if ("WIRE".equals(gpsStoreBranchVo.getGpsCategory())) {
				gpsStoreBranchVo.setGpsCategoryName("有线");
			} else if("WIRELESS".equals(gpsStoreBranchVo.getGpsCategory())) {
				gpsStoreBranchVo.setGpsCategoryName("无线");
			}else{
				gpsStoreBranchVo.setGpsCategoryName("");
			}
			// 设置状态
			GpsStatus gpsStatus = GpsStatus.fromValue(gpsStoreBranchVo.getGpsStatus());
			if (gpsStatus != null) {
				gpsStoreBranchVo.setGpsStatusName(gpsStatus.getRemark());
			}
			// 设置供应商名称
			for (GpsSupplier gpsSupplier : gpsSupplierList) {
				if (gpsSupplier.getSupplierId().equals(gpsStoreBranchVo.getSupplierId())) {
					gpsStoreBranchVo.setSupplierName(gpsSupplier.getSupplierName());
				}
			}
			// 设置经销商名称
			// TODO 预留接口：信贷系统查询所有经销商。
			/**
			 * 测试
			 */
			List<GpsBranchVo> branchList = new ArrayList<GpsBranchVo>();
			GpsBranchVo gpsBranch = new GpsBranchVo();
			gpsBranch.setBranchId("A102");
			branchList.add(gpsBranch);
			for (GpsBranchVo gpsBranchVo : branchList) {
				if (gpsBranchVo.getBranchId().equals(gpsStoreBranchVo.getBranchId())) {
					gpsBranchVo.setBranchName(gpsStoreBranchVo.getBranchName());
					break;
				}
			}
			listVo.add(gpsStoreBranchVo);
		}
		return listVo;
	}

	@Override
	public GpsStoreBranch delBranchStore(String storeId) {
		GpsStoreBranch gpsStoreBranch = new GpsStoreBranch();
		gpsStoreBranch.setStoreId(storeId);
		//删除收货表
		gpsStoreBranch = gpsStoreBranchMapper.selectByPrimaryKey(storeId);
		String gpsId = gpsStoreBranch.getGpsId();
//		GpsReceive gpsReceive = gpsReceiveMapperImpl.selectByGpsId(gpsId);
		List<GpsReceive> gpsReceiveList = gpsReceiveMapperImpl.selectByGpsIdAndBranchCode(gpsId,gpsStoreBranch.getBranchId());
		for (int i = 0; i < gpsReceiveList.size(); i++) {
			gpsReceiveMapperImpl.deleteByPrimaryKey(gpsReceiveList.get(i));
		}
		
		//删除经销商库存
		gpsStoreBranchMapper.deleteByPrimaryKey(storeId);
		return gpsStoreBranch;
	}

	@Override
	public GpsStorePj delPjStore(String storeId) {
		GpsStorePj gpsStorePj = new GpsStorePj();
		gpsStorePj.setStoreId(storeId);
		gpsStorePjMapper.deleteByPrimaryKey(storeId);
		return gpsStorePj;
	}

	@Override
	public GpsStorePj delPjStore(GpsStorePj gpsStorePj) {
		gpsStorePjMapper.deleteByGpsId(gpsStorePj.getGpsId());
		return gpsStorePj;
	}

	@Override
	public GpsStorePj lockStorePj(String gpsId) {
		GpsStorePj gpsStorePj = gpsStorePjMapper.selectStorePjByGpsId(gpsId);
		Assert.isTrue(gpsStorePj != null, "锁定潽金库存GPS,GPS编码【"+gpsId+"】在潽金库存不存在");
		//锁定潽金库存
		gpsStorePj.setGpsStatus(GpsStatus.STORE_LOCK.getCode());
		gpsStorePjMapper.updateByPrimaryKeySelective(gpsStorePj);
		return gpsStorePj;
	}

	@Override
	public GpsStorePj unLockStorePj(String gpsId) {
		GpsStorePj gpsStorePj = gpsStorePjMapper.selectStorePjByGpsId(gpsId);
		Assert.isTrue(gpsStorePj != null, "解锁潽金库存GPS,GPS编码【"+gpsId+"】在潽金库存不存在");
		//解锁潽金库存
		gpsStorePj.setGpsStatus(GpsStatus.STORE_FREE.getCode());
		gpsStorePjMapper.updateByPrimaryKeySelective(gpsStorePj);
		return gpsStorePj;
	}

	@Override
	public boolean isStoreExist(String gpsId, List<GpsStorePj> allStorePj, List<GpsStoreBranch> allStoreBranch) {
		boolean isExistPj = false;
		boolean isExistBranch = false;
		for (GpsStorePj currGpsStorePj : allStorePj) {
			if(currGpsStorePj.getGpsId().equals(gpsId)){
				isExistPj = true;
				break;
			}
		}
		if(gpsDuplicatedSwitch){
			Assert.isTrue(!(isExistPj), "潽金库存已存在此gps编码："+gpsId);
		}
		
		for (GpsStoreBranch currGpsStoreBranch : allStoreBranch) {
			if(currGpsStoreBranch.getGpsId().equals(gpsId)){
				isExistBranch = true;
				break;
			}
		}
		if(gpsDuplicatedSwitch){
			Assert.isTrue(!(isExistBranch), "经销商库存已存在此gps编码："+gpsId);
		}
		return isExistPj || isExistBranch;
	}
}
