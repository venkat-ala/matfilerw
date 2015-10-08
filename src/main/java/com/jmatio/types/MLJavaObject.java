/*
 * Copyright (c) 2006, Wojciech Gradkowski
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 *  - Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 *    disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other materials provided with the distribution.
 *  - Neither the name of JMatIO nor the names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jmatio.types;

import java.io.ObjectInputStream;
import java.nio.ByteBuffer;

import com.jmatio.io.stream.ByteBufferInputStream;

@SuppressWarnings("rawtypes")
public class MLJavaObject extends MLArray {

	private final String className;
	private final MLNumericArray content;

	public MLJavaObject(String name, String className, MLNumericArray content) {
		super(name, new int[]{1, 1}, MLArray.mxOPAQUE_CLASS, 0);

		this.className = className;
		this.content = content;
	}

	public String getClassName() {
		return className;
	}

	public ByteBuffer getContent() {
		return content.getRealByteBuffer();
	}

	/** Attempts to instantiate the Java Object, and all kinds of stuff can go wrong. */
	public Object instantiateObject() throws Exception {
		// de-serialize object
		ObjectInputStream ois = new ObjectInputStream(
				new ByteBufferInputStream(content.getRealByteBuffer(), content.getRealByteBuffer().limit()));
		try {
			return ois.readObject();
		} finally {
			ois.close();
		}
	}
}