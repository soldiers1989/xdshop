package com.pujjr.gps.common;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.pujjr.common.exception.CheckFailException;
import com.pujjr.gps.dal.domain.GpsBrand;
import com.pujjr.gps.dal.domain.GpsOrderDetail;
import com.pujjr.gps.dal.domain.GpsSupplier;
import com.pujjr.gps.enumeration.GpsType;

/**
 * @author wen
 * @date 创建时间：2017年11月30日 下午7:50:23
 *
 */
public class MatchUtils {

	/**
	 * 根据品牌名称获得匹配的品牌编号
	 * 
	 * @param gpsBrandList
	 * @param brandId
	 * @return
	 * @throws CheckFailException
	 */
	public static String findMatchGpsBrandName(List<GpsBrand> gpsBrandList, String brandName) throws CheckFailException {
		if (gpsBrandList != null) {
			for (GpsBrand gpsBrand : gpsBrandList) {
				if (StringUtils.equals(brandName, gpsBrand.getBrandName())) {
					return gpsBrand.getBrandId();
				}
			}
		}
		throw new CheckFailException("未找到匹配品牌名称[" + brandName + "]的品牌信息");
	}

	/**
	 * 找到匹配的订单明细
	 * 
	 * @param gpsOrderDetailList
	 * @param detailId
	 * @return
	 * @throws CheckFailException
	 */
	public static GpsOrderDetail findMatchGpsOrderDetail(List<GpsOrderDetail> gpsOrderDetailList, String detailId) throws CheckFailException {
		if (gpsOrderDetailList != null) {
			for (GpsOrderDetail gpsOrderDetail : gpsOrderDetailList) {
				if (StringUtils.equals(detailId, gpsOrderDetail.getDetailId())) {
					return gpsOrderDetail;
				}
			}
		}
		throw new CheckFailException("未找到匹配明细编号[" + detailId + "]的GPS明细记录");
	}

	/**
	 * 找到匹配的供应商
	 * 
	 * @param gpsSupplierList
	 * @param supplierId
	 * @return
	 * @throws CheckFailException
	 */
	public static GpsSupplier findMatchGpsSupplier(List<GpsSupplier> gpsSupplierList, String supplierId) throws CheckFailException {
		if (gpsSupplierList != null) {
			for (GpsSupplier gpssupplier : gpsSupplierList) {
				if (StringUtils.equals(supplierId, gpssupplier.getSupplierId())) {
					return gpssupplier;
				}
			}
		}
		throw new CheckFailException("未找到供应商编号[" + supplierId + "]的供应商");
	}

	/**
	 * gps类型匹配
	 * 
	 * @param gpsCatory
	 * @return
	 * @throws CheckFailException
	 */
	public static String findMatchGpsCategoryCode(String gpsCatory) throws CheckFailException {
		Assert.isTrue(StringUtils.isNotBlank(gpsCatory), "GPS设备类型不能为空");
		for (GpsType type : GpsType.values()) {
			if (StringUtils.equals(gpsCatory, type.getRemark())) {
				return type.name();
			}
		}
		throw new CheckFailException("GPS设备类型 " + gpsCatory + " 错误");
	}
}
