package org.jeecg.modules.exam.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.apache.commons.beanutils.BeanUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.exam.entity.Classroom;
import org.jeecg.modules.exam.entity.Course;
import org.jeecg.modules.exam.entity.ExamArrange;
import org.jeecg.modules.exam.entity.ExamArrangeVO;
import org.jeecg.modules.exam.mapper.ClassroomMapper;
import org.jeecg.modules.exam.mapper.CourseMapper;
import org.jeecg.modules.exam.service.IExamArrangeService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.mapper.SysUserMapper;
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
 * @Description: 考试安排表
 * @Author: jeecg-boot
 * @Date:   2020-04-15
 * @Version: V1.0
 */
@Api(tags="考试安排表")
@RestController
@RequestMapping("/exam/examArrange")
@Slf4j
public class ExamArrangeController extends JeecgController<ExamArrange, IExamArrangeService> {
	 @Autowired
	 private IExamArrangeService examArrangeService;
	 @Autowired
	 private ClassroomMapper classroomMapper;
	 @Autowired
	 private CourseMapper courseMapper;
	 @Autowired
	 private SysUserMapper sysUserMapper;
	/**
	 * 分页列表查询
	 *
	 * @param examArrange
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "考试安排表-分页列表查询")
	@ApiOperation(value="考试安排表-分页列表查询", notes="考试安排表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ExamArrange examArrange,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) throws InvocationTargetException, IllegalAccessException {
		QueryWrapper<ExamArrange> queryWrapper = QueryGenerator.initQueryWrapper(examArrange, req.getParameterMap());
		Page<ExamArrange> page = new Page<ExamArrange>(pageNo, pageSize);
		IPage<ExamArrange> pageList = examArrangeService.page(page, queryWrapper);
		IPage<ExamArrangeVO> pageList2 = new IPage<ExamArrangeVO>() {
			@Override
			public List<OrderItem> orders() {
				return null;
			}

			@Override
			public List<ExamArrangeVO> getRecords() {
				return null;
			}

			@Override
			public IPage<ExamArrangeVO> setRecords(List<ExamArrangeVO> records) {
				return null;
			}

			@Override
			public long getTotal() {
				return 0;
			}

			@Override
			public IPage<ExamArrangeVO> setTotal(long total) {
				return null;
			}

			@Override
			public long getSize() {
				return 0;
			}

			@Override
			public IPage<ExamArrangeVO> setSize(long size) {
				return null;
			}

			@Override
			public long getCurrent() {
				return 0;
			}

			@Override
			public IPage<ExamArrangeVO> setCurrent(long current) {
				return null;
			}
		};
		BeanUtils.copyProperties(pageList,pageList2);
		List<ExamArrangeVO> ExamArrangeVOList = new ArrayList<ExamArrangeVO>();
		for(ExamArrange examArrangePage : pageList.getRecords()){
			ExamArrangeVO examArrangeVO = new ExamArrangeVO();
			Classroom classroom = classroomMapper.selectOne(new QueryWrapper<Classroom>().eq("exam_no",examArrangePage.getExamNo()));
			examArrangeVO.setName(classroom.getName());
			Course course =courseMapper.selectOne(new QueryWrapper<Course>().eq("course_no",examArrangePage.getCourseNo()));
			examArrangeVO.setCourseName(course.getCourseName());
			SysUser sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("work_no",examArrangePage.getTeacherNo()));
			examArrangeVO.setRealname(sysUser.getRealname());
			BeanUtils.copyProperties(examArrangePage,examArrangeVO);
			examArrangeVO.setId(examArrangePage.getId());
			ExamArrangeVOList.add(examArrangeVO);
		}
		log.info("{}",ExamArrangeVOList);
		pageList2.setRecords(ExamArrangeVOList);
		log.info("{}",pageList2.getRecords());
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param examArrange
	 * @return
	 */
	@AutoLog(value = "考试安排表-添加")
	@ApiOperation(value="考试安排表-添加", notes="考试安排表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ExamArrange examArrange) {
		examArrangeService.save(examArrange);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param examArrange
	 * @return
	 */
	@AutoLog(value = "考试安排表-编辑")
	@ApiOperation(value="考试安排表-编辑", notes="考试安排表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ExamArrange examArrange) {
		examArrangeService.updateById(examArrange);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "考试安排表-通过id删除")
	@ApiOperation(value="考试安排表-通过id删除", notes="考试安排表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		examArrangeService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "考试安排表-批量删除")
	@ApiOperation(value="考试安排表-批量删除", notes="考试安排表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.examArrangeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "考试安排表-通过id查询")
	@ApiOperation(value="考试安排表-通过id查询", notes="考试安排表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ExamArrange examArrange = examArrangeService.getById(id);
		if(examArrange==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(examArrange);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param examArrange
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ExamArrange examArrange) {
        return super.exportXls(request, examArrange, ExamArrange.class, "考试安排表");
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
        return super.importExcel(request, response, ExamArrange.class);
    }

}
