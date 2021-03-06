package org.jeecg.modules.exam.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.exam.entity.ExamInfo;
import org.jeecg.modules.exam.service.IExamInfoService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 考试须知表
 * @Author: jeecg-boot
 * @Date:   2020-04-15
 * @Version: V1.0
 */
@Api(tags="考试须知表")
@RestController
@RequestMapping("/exam/examInfo")
@Slf4j
public class ExamInfoController extends JeecgController<ExamInfo, IExamInfoService> {
	@Autowired
	private IExamInfoService examInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param examInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "考试须知表-分页列表查询")
	@ApiOperation(value="考试须知表-分页列表查询", notes="考试须知表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ExamInfo examInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ExamInfo> queryWrapper = QueryGenerator.initQueryWrapper(examInfo, req.getParameterMap());
		Page<ExamInfo> page = new Page<ExamInfo>(pageNo, pageSize);
		IPage<ExamInfo> pageList = examInfoService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param examInfo
	 * @return
	 */
	@AutoLog(value = "考试须知表-添加")
	@ApiOperation(value="考试须知表-添加", notes="考试须知表-添加")
	@PostMapping(value = "/add")
	@RequiresPermissions("examinfo:add")
	public Result<?> add(@RequestBody ExamInfo examInfo) {
		examInfoService.save(examInfo);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param examInfo
	 * @return
	 */
	@AutoLog(value = "考试须知表-编辑")
	@ApiOperation(value="考试须知表-编辑", notes="考试须知表-编辑")
	@PutMapping(value = "/edit")
	@RequiresPermissions("examinfo:edit")
	public Result<?> edit(@RequestBody ExamInfo examInfo) {
		examInfoService.updateById(examInfo);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "考试须知表-通过id删除")
	@ApiOperation(value="考试须知表-通过id删除", notes="考试须知表-通过id删除")
	@DeleteMapping(value = "/delete")
	@RequiresPermissions("examinfo:delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		examInfoService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "考试须知表-批量删除")
	@ApiOperation(value="考试须知表-批量删除", notes="考试须知表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.examInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "考试须知表-通过id查询")
	@ApiOperation(value="考试须知表-通过id查询", notes="考试须知表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ExamInfo examInfo = examInfoService.getById(id);
		if(examInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(examInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param examInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ExamInfo examInfo) {
        return super.exportXls(request, examInfo, ExamInfo.class, "考试须知表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ExamInfo.class);
    }

}
