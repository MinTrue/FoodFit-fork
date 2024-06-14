package com.sds.foodfit.model.favoritefood;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.foodfit.domain.FavoriteFood;
import com.sds.foodfit.domain.Member;

@Mapper
public interface FavoriteFoodDAO {

    public boolean insertFavoriteFood(Member member, FavoriteFood favoriteFood); // 즐겨찾기 등록

    public FavoriteFood selectUserFavorite(int memberIdx); // 유저의 favoriteFood 객체 가져오기

    public void deleteOnefood(int foodIdx); // 유저의 favoriteFood 중 1개의 foodDB 삭제하기

    public void deleteMultiFood(List<Integer> foodIdxList); // 유저의 favoriteFood 중 1개의 foodDB 삭제하기

}
