package com.sds.foodfit.model.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.foodfit.domain.Allergy;

@Mapper
public interface AllergyDAO {

	public int insert(Allergy allergy);
	public List selectAll();
	public Allergy select(int allergy_idx);
	public void update(Allergy allergy);
}
