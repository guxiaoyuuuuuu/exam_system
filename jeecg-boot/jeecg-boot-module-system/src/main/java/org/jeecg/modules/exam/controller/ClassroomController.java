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
import org.jeecg.modules.exam.entity.Classroom;
import org.jeecg.modules.exam.service.IClassroomService;

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
 * @Description: 教室信息
 * @Author: jeecg-boot
 * @Date:   2020-04-15
 * @Version: V1.0
 */
@Api(tags="教室信息")
@RestController
@RequestMapping("/exam/classroom")
@Slf4j
public class ClassroomController extends JeecgController<Classroom, IClassroomService> {
	@Autowired
	private IClassroomService classroomService;
	
	/**
	 * 分页列表查询
	 *
	 * @param classroom
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "教室信息-分页列表查询")
	@ApiOperation(value="教室信息-分页列表查询", notes="教室信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Classroom classroom,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Classroom> queryWrapper = QueryGenerator.initQueryWrapper(classroom, req.getParameterMap());
		Page<Classroom> page = new Page<Classroom>(pageNo, pageSize);
		IPage<Classroom> pageList = classroomService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param classroom
	 * @return
	 */
	@AutoLog(value = "教室信息-添加")
	@ApiOperation(value="教室信息-添加", notes="教室信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Classroom classroom) {
		classroomService.save(classroom);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param classroom
	 * @return
	 */
	@AutoLog(value = "教室信息-编辑")
	@ApiOperation(value="教室信息-编辑", notes="教室信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Classroom classroom) {
		classroomService.updateById(classroom);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "教室信息-通过id删除")
	@ApiOperation(value="教室信息-通过id删除", notes="教室信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		classroomService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "教室信息-批量删除")
	@ApiOperation(value="教室信息-批量删除", notes="教室信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.classroomService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "教室信息-通过id查询")
	@ApiOperation(value="教室信息-通过id查询", notes="教室信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Classroom classroom = classroomService.getById(id);
		if(classroom==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(classroom);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param classroom
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Classroom classroom) {
        return super.exportXls(request, classroom, Classroom.class, "教室信息");
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
        return super.importExcel(request, response, Classroom.class);
    }

}
