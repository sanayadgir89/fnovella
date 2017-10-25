package org.fnovella.project.course.controller;

import java.util.ArrayList;
import java.util.List;

import org.fnovella.project.course.model.Course;
import org.fnovella.project.course.repository.CourseRepository;
import org.fnovella.project.inscriptions_inst_course.repository.InscriptionsInstCourseRepository;
import org.fnovella.project.inscriptions_part_course.repository.InscriptionsPartCourseRepository;
import org.fnovella.project.utility.model.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course/")
public class CourseController {
	
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private InscriptionsInstCourseRepository inscriptionsInstCourseRepository;
	@Autowired
	private InscriptionsPartCourseRepository inscriptionsPartCourseRepository;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public APIResponse getAll(@RequestHeader("authorization") String authorization, Pageable pageable) {
		return new APIResponse(this.courseRepository.findAll(pageable), null);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public APIResponse get(@PathVariable("id") Integer id, @RequestHeader("authorization") String authorization) {
		ArrayList<String> errors = new ArrayList<String>();
		Course course = this.courseRepository.findOne(id);
		if (course != null) {
			return new APIResponse(this.courseRepository.findOne(id), null);
		}
		errors.add("Course doesn't exist");
		return new APIResponse(null, errors);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public APIResponse create(@RequestHeader("authorization") String authorization, @RequestBody Course course) {
		ArrayList<String> errors = course.validate();
		if (errors.size() == 0) {
			return new APIResponse(this.courseRepository.save(course), null);
		}
		return new APIResponse(null, errors);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PATCH)
	public APIResponse update(@RequestHeader("authorization") String authorization, @RequestBody Course course,
			@PathVariable("id") Integer id) {
		ArrayList<String> errors = new ArrayList<String>();
		Course toUpdate = this.courseRepository.findOne(id);
		if (toUpdate != null) {
			toUpdate.setUpdateFields(course);
			return new APIResponse(this.courseRepository.saveAndFlush(toUpdate), null);
		}
		errors.add("Course doesn't exist");
		return new APIResponse(null, errors);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public APIResponse delete(@RequestHeader("authorization") String authorization, @PathVariable("id") Integer id) {
		ArrayList<String> errors = new ArrayList<String>();
		Course toDelete = this.courseRepository.findOne(id);
		if (toDelete != null) {
			List<?> list = this.inscriptionsPartCourseRepository.findByCourseId(id);
			if (list != null && !list.isEmpty())
				this.inscriptionsPartCourseRepository.deleteByCourseId(id);
			list = this.inscriptionsInstCourseRepository.findByCourseId(id);
			if (list != null && !list.isEmpty())
				this.inscriptionsInstCourseRepository.delete(id);
			this.courseRepository.delete(toDelete);
			return new APIResponse(true, null);
		}
		errors.add("Course doesn't exist");
		return new APIResponse(null, errors);
	}
}