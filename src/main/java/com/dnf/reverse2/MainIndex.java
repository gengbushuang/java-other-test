package com.dnf.reverse2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

import com.dnf.Cond;
import com.dnf.model.Audience;
import com.dnf.reverse2.index.IndexBuild;
import com.dnf.reverse2.query.BoolQuery;

public class MainIndex {
	
	
	public static List<Audience> createAudiences() {
		List<Audience> audiences = new ArrayList<>();
		Audience audience = new Audience();
		audience.setAge_range(3);
		audience.setCountrys("上海");
		audience.setCountry_support_mode(1);
		audience.setId(0);
		audiences.add(audience);
		
		audience = new Audience();
		audience.setGender(1);
		audience.setCountrys("北京");
		audience.setCountry_support_mode(1);
		audience.setId(1);
		audiences.add(audience);
		
		audience = new Audience();
		audience.setGender(2);
		audience.setAge_range(3);
		audience.setId(2);
		audiences.add(audience);
		
		audience = new Audience();
		audience.setCountrys("北京,上海");
		audience.setCountry_support_mode(0);
		audience.setId(3);
		audiences.add(audience);
		
		audience = new Audience();
		audience.setAge_range(3);
		audience.setGender(1);
		audience.setCountrys("北京");
		audience.setCountry_support_mode(0);
		audience.setId(4);
		audiences.add(audience);
		
		audience = new Audience();
		audience.setGender(2);
		audience.setCountrys("北京");
		audience.setCountry_support_mode(1);
		audience.setId(5);
		audiences.add(audience);
		
		audience = new Audience();
		audience.setAge_range(34);
		audience.setId(6);
		audiences.add(audience);
		
		audience = new Audience();
		audience.setGender(1);
		audience.setCountrys("北京");
		audience.setCountry_support_mode(1);
		audience.setId(7);
		audiences.add(audience);
		
		audience = new Audience();
		audience.setCountrys("北京,上海");
		audience.setCountry_support_mode(0);
		audience.setId(8);
		audiences.add(audience);
		
		audience = new Audience();
		audience.setAge_range(34);
		audience.setId(9);
		audiences.add(audience);
		
		audience = new Audience();
		audience.setCountrys("北京,上海");
		audience.setCountry_support_mode(0);
		audience.setId(10);
		audiences.add(audience);
		
		audience = new Audience();
		audience.setAge_range(3);
		audience.setCountrys("上海");
		audience.setCountry_support_mode(1);
		audience.setId(11);
		audiences.add(audience);
		
		audience = new Audience();
		audience.setGender(1);
		audience.setCountrys("北京");
		audience.setCountry_support_mode(1);
		audience.setId(12);
		audiences.add(audience);
		
		audience = new Audience();
		audience.setAge_range(3);
		audience.setCountrys("上海");
		audience.setCountry_support_mode(1);
		audience.setId(13);
		audiences.add(audience);
		
		audience = new Audience();
		audience.setGender(2);
		audience.setCountrys("北京");
		audience.setCountry_support_mode(1);
		audience.setId(14);
		audiences.add(audience);
		
		return audiences;
	}
	
	public static Index readEliminateIndex(String path) {
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(path)))) {
			Object readObject = inputStream.readObject();
			if (readObject != null) {
				return  (Index) readObject;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void wirteEliminateIndex(Index index,String path) {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(path)))) {
			outputStream.writeObject(index);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		String path = "D:/index.log";
//		Index index = new Index();
//		IndexBuild indexBuild = new IndexBuild();
//		
//		List<Audience> audiences = createAudiences();
//		
//		for(Audience audience:audiences) {
//			indexBuild.appendIndex(index, audience);
//		}
		
		
//		wirteEliminateIndex(index, path);
		
		Index index = readEliminateIndex(path);
		
		System.out.println(index);
		for(int i = 0; i<10;i++) {
		long n = System.currentTimeMillis();
		Cond[] conds = { new Cond("country", "广州"),new Cond("age", "3")};
		BoolQuery boolQuery = new BoolQuery();
		int[] query = boolQuery.query(conds, index);
		System.out.println(System.currentTimeMillis()-n);
		
		System.out.println(Arrays.toString(query));
		}
	}
}
