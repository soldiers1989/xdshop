package com.pujjr.gps.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.pujjr.gps.api.LockParam;
import com.pujjr.gps.api.StoreBranchParam;
import com.pujjr.gps.api.StorePjParam;
import com.pujjr.gps.dal.domain.GpsStocktake;
import com.pujjr.gps.dal.domain.GpsStoreBranch;
import com.pujjr.gps.dal.domain.GpsStorePj;
import com.pujjr.gps.vo.GpsInstallHisVo;
import com.pujjr.gps.vo.GpsInstallPjVo;
import com.pujjr.gps.vo.GpsStoreBranchVo;
import com.pujjr.gps.vo.GpsStorePjVo;
import com.pujjr.gps.vo.StoreInfoVo;

public interface StoreService {
	/**
	 * 获取潽金库存列表
	 * 
	 * @param storePjParam
	 * @return
	 */
	public PageInfo<GpsStorePjVo> getPjStore(StorePjParam storePjParam);
	
	/**
	 * 获取所有潽金库存
	 * @param storePjParam
	 * @return
	 */
	public List<GpsStorePjVo> getAllPjStore(StorePjParam storePjParam);

	/**
	 * 保存潽金库存
	 * 
	 * @param gpsInstallPjVo
	 *            入库记录对象
	 * @return 潽金入库记录对象
	 */
	public GpsStorePj savePjStore(GpsInstallPjVo gpsInstallPjVo);

	/**
	 * 批量新增潽金库存
	 * 
	 * @param file
	 * @param consigner
	 * @return
	 * @throws Exception
	 */
	public List<GpsStorePjVo> batchAddPjStore(MultipartFile file, String consigner) throws Exception;

	/**
	 * 潽金新增库存
	 * 
	 * @param gpsStore
	 * @param createAccountId
	 * @return
	 */
	public GpsStorePj singleAddPjStore(GpsStorePj gpsStore, String createAccountId);

	/**
	 * 获取经销商库存列表
	 * 
	 * @param storeBranchParam
	 * @return
	 */
	public PageInfo<GpsStoreBranchVo> getBaseBranchStore(StoreBranchParam storeBranchParam);

	/**
	 * 获取经销商库存列表
	 * 
	 * @param storeBranchParam
	 * @return
	 */
	public PageInfo<GpsStoreBranchVo> getBranchStore(GpsStoreBranchVo gpsStoreBranchVo);
	
	/**
	 * 获取所有经销商库存
	 * @param gpsStoreBranchVo
	 * @return
	 */
	public List<GpsStoreBranchVo> getAllBranchStore(GpsStoreBranchVo gpsStoreBranchVo);

	/**
	 * 单笔新增经销商库存
	 * 
	 * @param gpsInstallHisVo
	 *            经销商入库历史
	 * @return
	 */
	public GpsStoreBranch saveBranchStore(@RequestBody GpsInstallHisVo gpsInstallHisVo);
	
	/**
	 * 删除库存
	 * @param storeId
	 * @return
	 */
	public GpsStoreBranch delBranchStore(String storeId);
	/**
	 * 删除库存
	 * @param storeId
	 * @return
	 */
	public GpsStorePj delPjStore(String storeId);

	/**
	 * 根据gps编码删除潽金库存
	 * @author tom
	 * @time 2017年12月27日 下午2:21:37
	 * @param gpsStorePj 待删除对象,根据对象中的GPS编码，执行删除操作
	 * @return 已删除对象
	 */
	public GpsStorePj delPjStore(GpsStorePj gpsStorePj);
	/**
	 * 批量新增经销商库存
	 * 
	 * @param file
	 * @param consigner
	 * @return
	 * @throws Exception
	 */
	public List<GpsStoreBranchVo> batchAddBranchStore(MultipartFile file, String consigner) throws Exception;

	/**
	 * gps锁定
	 * 
	 * @param gpsId
	 * @param appId
	 * @return
	 */
	public GpsStoreBranch lock(String gpsId, String appId);

	/**
	 * gps解锁
	 * 
	 * @param gpsId
	 * @param appId
	 * @return
	 */
	public GpsStoreBranch unlock(String gpsId);
	
	/**
	 * gps解锁
	 * 
	 * @param gpsId
	 * @param appId
	 * @return
	 */
	public GpsStorePj unlockPj(String gpsId);

	/**
	 * gps出库
	 * 
	 * @param gpsId
	 * @return
	 */
	public GpsStoreBranch outStore(String gpsId);

	/**
	 * 禁用/启用经销商gps
	 * 
	 * @param gpsId
	 * @return
	 */
	public GpsStoreBranch disabelStoreBranch(String gpsId);
	
	/**
	 * 禁用/启用潽金gps
	 * 
	 * @param gpsId
	 * @return
	 */
	public GpsStorePj disabelStorePj(String gpsId);

	/**
	 * 临时盘点
	 * 
	 * @param branchId
	 * @return
	 */
	public GpsStocktake tempCheckBranchStore(String branchId);

	/**
	 * 盘点全部经销商库存
	 * 
	 * @return
	 */
	public int checkAllBranchStore();

	/**
	 * 批量锁定解锁
	 * 
	 * @param lockParm
	 * @return
	 */
	public List<GpsStoreBranch> batchLock(LockParam lockParm);

	/**
	 * 获取库存总量信息
	 * @param gpsStoreBranchVo
	 * @return
	 */
	public StoreInfoVo getStoreInfo(GpsStoreBranchVo gpsStoreBranchVo);

	/**
	 * 恢复GPS
	 * @param gpsId
	 * @return
	 */
	public GpsStoreBranch recover(String gpsId);

	/**
	 * 删除GPS
	 * 
	 * @param gpsId
	 * @return
	 */
	public int delete(String gpsId);
	
	/**
	 * 潽金库存新增，校验潽金库存是否存在当前新增GPS编码
	 * @param gpsInstallPjVo 待插入历史（包含插入库存信息）
	 * @param List<GpsStorePj> allStore 所有潽金库存
	 * @return true:存在  false：不存在
	 */
	public boolean isStorePjExist(GpsInstallPjVo gpsInstallPjVo,List<GpsStorePj> allStore);
	/**
	 * 经销商库存新增，校验经销商库存是否存在当前新增GPS编码
	 * @param gpsInstallHisVo 待插入历史（包含插入库存信息）
	 * @param List<GpsStoreBranch> allStore 所有经销商库存
	 * @return true:存在  false：不存在
	 */
	public GpsStoreBranch isStoreBranchExist(GpsInstallHisVo gpsInstallHisVo,List<GpsStoreBranch> allStore);
	/**
	 * 校验gps编码是否在潽金和经销商库存已存在
	 * @author tom
	 * @time 2017年12月29日 下午5:05:41
	 * @param gpsId
	 * @param allStorePj
	 * @param allStoreBranch
	 * @return
	 */
	public boolean isStoreExist(String gpsId,List<GpsStorePj> allStorePj,List<GpsStoreBranch> allStoreBranch);
	
	/**
	 * 锁定潽金库存
	 * @author tom
	 * @time 2017年12月27日 下午3:55:34
	 * @param gpsId
	 * @return
	 */
	public GpsStorePj lockStorePj(String gpsId);
	/**
	 * 解锁潽金库存
	 * @author tom
	 * @time 2017年12月27日 下午3:55:43
	 * @param gpsId
	 * @return
	 */
	public GpsStorePj unLockStorePj(String gpsId);

}
