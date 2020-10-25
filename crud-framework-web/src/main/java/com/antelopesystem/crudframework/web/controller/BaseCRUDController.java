package com.antelopesystem.crudframework.web.controller;

import com.antelopesystem.crudframework.model.BaseCrudEntity;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import com.antelopesystem.crudframework.ro.BaseRO;
import com.antelopesystem.crudframework.web.ro.ResultRO;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

public abstract class BaseCRUDController<ID extends Serializable, Entity extends BaseCrudEntity<ID>, ReturnRO extends BaseRO<ID>> extends BaseDifferentRoCRUDController<ID, Entity, ReturnRO , ReturnRO, ReturnRO, ReturnRO> {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResultRO show(@PathVariable ID id) {
		return super.show(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResultRO index(DynamicModelFilter filter) {
		return super.index(filter);
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	public ResultRO search(@RequestBody DynamicModelFilter filter) {
		return super.search(filter);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResultRO create(@RequestBody ReturnRO ro) {
		return super.create(ro);
	}

	@RequestMapping(value = "/many", method = RequestMethod.POST)
	@ResponseBody
	public ResultRO createMany(@RequestBody List<ReturnRO> ros) {
		return super.createMany(ros);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResultRO update(@PathVariable ID id, @RequestBody ReturnRO ro) {
		return super.update(id, ro);
	}

	@RequestMapping(value = "/many", method = RequestMethod.PUT)
	@ResponseBody
	public ResultRO updateMany(@RequestBody List<ReturnRO> ros) {
		return super.updateMany(ros);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResultRO delete(@PathVariable ID id) {
		return super.delete(id);
	}

	@RequestMapping(value = "/many", method = RequestMethod.DELETE)
	@ResponseBody
	public ResultRO deleteMany(@RequestBody List<ID> ids) {
		return super.deleteMany(ids);
	}

}