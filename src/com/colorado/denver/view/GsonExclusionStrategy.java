package com.colorado.denver.view;

import com.colorado.denver.model.BaseEntity;
import com.colorado.denver.model.User;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class GsonExclusionStrategy implements ExclusionStrategy {

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		if (f.getDeclaringClass() == User.class && f.getName().equals(User.PASSWORD)) {
			return true;// Under no circumstances we need the pw in the frontend
		} else if (f.getDeclaringClass() == BaseEntity.class && f.getName().equals(BaseEntity.CRUD)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

}