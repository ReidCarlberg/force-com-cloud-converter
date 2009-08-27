/*
The MIT License

Copyright (c) 2008, 2009 Model Metrics, Inc.

http://ModelMetrics.com
http://ModelMetrics.com/authors/rcarlberg

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */

package com.modelmetrics.common.sforce.dao;

import java.util.Collection;


public interface Sproxy {

	public abstract String getId();

	public abstract String getType();
	
	public abstract String getValue(String key);
	
	public abstract boolean hasValue(String key);
	
	public abstract boolean hasNull(String key);
	
	public abstract void setValue(String key, Object value);
	
	public abstract void setNull(String key);
	
	public abstract Collection<String> getValueKeys();
	
	public abstract Collection<String> getNullKeys();
	
	public abstract void removeValue(String key);

	public abstract void removeNull(String key);
	
	public abstract Sproxy getParent();
	
	public abstract Collection<Sproxy> getChildren();
	
	public abstract Sproxy getChild(String key);

	public abstract boolean hasParent();
	
	public abstract boolean hasChildren();
	
	public abstract boolean isDirty();
}