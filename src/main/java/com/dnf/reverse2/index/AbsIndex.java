package com.dnf.reverse2.index;

import com.dnf.model.Audience;
import com.dnf.reverse2.Index;

public abstract class AbsIndex<T> {

	
	protected abstract T build(Audience audience,Index index);
}
