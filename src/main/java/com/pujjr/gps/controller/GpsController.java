/**
 * 
 */
package com.pujjr.gps.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.gps.controller.base.BaseController;

/**
 * @author wen
 * @date 创建时间：2017年6月28日 下午2:30:30
 *
 */
@RestController
@RequestMapping("/gps")
public class GpsController extends BaseController {

	/*@Autowired
	private GpsService gpsServiceImpl;
	@GetMapping(value = "/brand/list")
	@ApiOperation(value = "获取品牌列表")
	public List<GpsBrand> getApply() {
		try {
			List<GpsBrand> gpsBrandList = gpsServiceImpl.getGpsBrandList();
			return gpsBrandList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}*/
}
