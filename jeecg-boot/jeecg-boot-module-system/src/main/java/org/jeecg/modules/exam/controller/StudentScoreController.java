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
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.exam.entity.StudentScore;
import org.jeecg.modules.exam.service.IStudentScoreService;

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
 * @Description: 学生成绩表
 * @Author: jeecg-boot
 * @Date:   2020-04-15
 * @Version: V1.0
 */
@Api(tags="学生成绩表")
@RestController
@RequestMapping("/exam/studentScore")
@Slf4j
public class StudentScoreController extends JeecgController<StudentScore, IStudentScoreService> {
	@Autowired
	private IStudentScoreService studentScoreService;
	
	/**
	 * 分页列表查询
	 *
	 * @param studentScore
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "学生成绩表-分页列表查询")
	@ApiOperation(value="学生成绩表-分页列表查询", notes="学生成绩表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(StudentScore studentScore,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<StudentScore> queryWrapper = QueryGenerator.initQueryWrapper(studentScore, req.getParameterMap());
		Page<StudentScore> page = new Page<StudentScore>(pageNo, pageSize);
		IPage<StudentScore> pageList = studentScoreService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param studentScore
	 * @return
	 */
	@AutoLog(value = "学生成绩表-添加")
	@ApiOperation(value="学生成绩表-添加", notes="学生成绩表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody StudentScore studentScore) {
		studentScoreService.save(studentScore);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param studentScore
	 * @return
	 */
	@AutoLog(value = "学生成绩表-编辑")
	@ApiOperation(value="学生成绩表-编辑", notes="学生成绩表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody StudentScore studentScore) {
		studentScoreService.updateById(studentScore);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "学生成绩表-通过id删除")
	@ApiOperation(value="学生成绩表-通过id删除", notes="学生成绩表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		studentScoreService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "学生成绩表-批量删除")
	@ApiOperation(value="学生成绩表-批量删除", notes="学生成绩表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.studentScoreService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "学生成绩表-通过id查询")
	@ApiOperation(value="学生成绩表-通过id查询", notes="学生成绩表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		StudentScore studentScore = studentScoreService.getById(id);
		if(studentScore==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(studentScore);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param studentScore
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, StudentScore studentScore) {
        return super.exportXls(request, studentScore, StudentScore.class, "学生成绩表");
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
        return super.importExcel(request, response, StudentScore.class);
    }

}
