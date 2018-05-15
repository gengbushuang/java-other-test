package com.dnf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.dnf.test.AppSupportMode;
import com.dnf.test.Audience;
import com.dnf.test.Country;
import com.dnf.test.Language;
import com.dnf.test.Position;

public class MainTest {

	
	public List<Audience> createList(int count,int appCount){
		List<Audience> audiences = new ArrayList<Audience>();
		for(int i = 0;i<count;i++) {
			Audience audience = createAudience(appCount);
			audience.setId(i);
			audiences.add(audience);
		}
		return audiences;
	}
	
	AppSupportMode mode = new AppSupportMode();
	Country country = new Country();
	Language language = new Language();
	Position position = new Position();
	
	Random random = new Random();
	
	DnfBuild build = new DnfBuild();
	
	public Audience createAudience(int appCount) {
		Audience audience = new Audience();
		//app选择
		StringBuilder sb = new StringBuilder();
		for(int n = 0;n<appCount;n++) {
			if(sb.length()>0) {
				sb.append(",");
			}
			sb.append(mode.getV());
		}
		audience.setApps(sb.toString());
		//国家选择
		audience.setCountrys(country.getV());
		//app排除还是指定 0为排除,1为指定
		audience.setApp_support_mode(random.nextInt(2));
		//国家排除还是指定 0为排除,1为指定
		audience.setCountry_support_mode(random.nextInt(2));
		//语言选择
		audience.setLanguage(language.getV());
		return audience;
	}
	
	public void index() {
		//建立索引还没有优化,100000的建立需要10多分钟
		List<Audience> createList = createList(10000,50);
		long n =System.currentTimeMillis();
		for(Audience audience:createList) {
			build.AddDoc("ad"+audience.getId(), String.valueOf(audience.getId()), audience.toString());
		}
		System.out.println(System.currentTimeMillis()-n);
		System.out.println("--------------------------索引建完");
	}
	
	public void query() {
		for(;;) {
		//随机生成查询语句
		List<Audience> createList = createList(1000,10);
		for(Audience audience:createList) {
			long n = System.currentTimeMillis();
			String[] split = audience.getApps().split(",");
			List<Cond> conds = new ArrayList<>();
			for(String s:split) {
				conds.add(new Cond("apps",s));
			}
			conds.add(new Cond("la", audience.getLanguage()));
			conds.add(new Cond("cy", audience.getCountrys()));
			int[] search = build.search(conds.toArray(new Cond[0]));
			if(search.length!=0) {
				System.out.println("有数据了");
				System.out.println(System.currentTimeMillis()-n);
				System.out.println(Arrays.toString(search));
			}
//			System.out.println(System.currentTimeMillis()-n);
//			System.out.println(Arrays.toString(search));
		}
		}
	}
	
	public static void main(String[] args) {
		MainTest mainTest = new MainTest();
		//建立索引
		mainTest.index();
		//查询
		mainTest.query();
	}
}
