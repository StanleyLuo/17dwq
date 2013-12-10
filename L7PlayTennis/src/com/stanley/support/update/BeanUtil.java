package com.stanley.support.update;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class BeanUtil<T> {

	private Class<T> clazz;

	public BeanUtil(Class<T> cls) {
		clazz = cls;
	}

	public T fromJson(Reader in) {
		Gson gson = new Gson();
		JsonReader jsReader = new JsonReader(in);

		T result = null;
		try {
			// Class<T> entityClass = (Class<T>) ((ParameterizedType)
			// getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			result = gson.fromJson(jsReader, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public List<T> fromJson2List(Reader in) {
		Gson gson = new Gson();
		JsonReader jsReader = new JsonReader(in);

		List<T> result = null;
		try {
			result = gson.fromJson(jsReader, new TypeToken<List<T>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public T fromJson(String json) {
		Gson gson = new Gson();
		T result = null;
		try {
			result = gson.fromJson(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String toJson(T bean) {
		Gson gson = new Gson();
		String json = gson.toJson(bean, clazz);
		return json;
	}

	public void toJson(T bean, Writer out) {
		Gson gson = new Gson();
		JsonWriter jsWriter = new JsonWriter(out);

		gson.toJson(bean, clazz, jsWriter);
	}

	public void toJson(List<T> bean, Writer out) {
		Gson gson = new Gson();
		JsonWriter jsWriter = new JsonWriter(out);

		gson.toJson(bean, new TypeToken<List<T>>() {
		}.getType(), jsWriter);
	}

	public String toJson(List<T> bean) {
		Gson gson = new Gson();
		String json = gson.toJson(bean, new TypeToken<List<T>>() {
		}.getType());
		return json;
	}

	public void saveAsJsonFile(T bean, String filePath) throws IOException {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File(filePath), false);
			this.toJson(bean, fw);
		} catch (Exception e) {
			throw new IOException(e);
		} finally {
			if (fw != null) {
				fw.close();
			}
		}

	}

}