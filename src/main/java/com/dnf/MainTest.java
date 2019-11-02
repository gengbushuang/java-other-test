package com.dnf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dnf.model.AppSupportMode;
import com.dnf.model.Audience;
import com.dnf.model.Country;
import com.dnf.model.Language;
import com.dnf.model.Position;
import com.dnf.reverse2.Index;
import com.dnf.reverse2.IndexDNF;
import com.dnf.reverse2.query.BoolQuery;

public class MainTest {
	
	Index index = new Index();
	
	public List<Audience> createList2(int count, int appCount) {
		List<Audience> audiences = new ArrayList<Audience>();
		for (int i = 0; i < count; i++) {
			Audience audience = createAudience2(appCount);
			audience.setId(i);
			audiences.add(audience);
		}
		return audiences;
	}

	public List<Audience> createList(int count, int appCount) {
		List<Audience> audiences = new ArrayList<Audience>();
		for (int i = 0; i < count; i++) {
			Audience audience = createAudience(i,appCount);
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

	public Audience createAudience(int i,int appCount) {
		Audience audience = new Audience();
		// app选择
		if(i%3==0) {
		StringBuilder sb = new StringBuilder();
		for (int n = 0; n < appCount; n++) {
			if (sb.length() > 0) {
				sb.append(",");
			}
			sb.append(mode.getV());
		}
		audience.setApps(sb.toString());
		}
		// 国家选择
		audience.setCountrys(country.getV());
		// app排除还是指定 0为排除,1为指定
		audience.setApp_support_mode(random.nextInt(2));
		// 国家排除还是指定 0为排除,1为指定
		audience.setCountry_support_mode(random.nextInt(2));
		// 语言选择
		if(i%10==0) {
		audience.setLanguage(language.getV());
		}
		return audience;
	}
	
	public Audience createAudience2(int appCount) {
		Audience audience = new Audience();
		// app选择
		StringBuilder sb = new StringBuilder();
		for (int n = 0; n < appCount; n++) {
			if (sb.length() > 0) {
				sb.append(",");
			}
			sb.append(mode.getV());
		}
		audience.setApps(sb.toString());
		// 国家选择
		audience.setCountrys(country.getV());
		// app排除还是指定 0为排除,1为指定
		audience.setApp_support_mode(random.nextInt(2));
		// 国家排除还是指定 0为排除,1为指定
		audience.setCountry_support_mode(random.nextInt(2));
		// 语言选择
		audience.setLanguage(language.getV());
		return audience;
	}

	public void index() {
		IndexDNF indexDNF = new IndexDNF();
		// 建立索引还没有优化,100000的建立需要10多分钟
		List<Audience> createList = createList(100, 50);
		long n = System.currentTimeMillis();
		for (Audience audience : createList) {
			try {
			indexDNF.appendIndex(index, audience.toString(), audience.getId());
			//build.AddDoc("ad" + audience.getId(), String.valueOf(audience.getId()), audience.toString());
			}catch(Exception e) {
				System.out.println(audience);
				System.exit(1);
			}
		}
		System.out.println(System.currentTimeMillis() - n);
		System.out.println("--------------------------索引建完");
	}

	public void query() {
		BoolQuery boolQuery = new BoolQuery();
		for (;;) {
			// 随机生成查询语句
			List<Audience> createList = createList2(1000, 10);
			for (Audience audience : createList) {
				long n = System.currentTimeMillis();
				String[] split = audience.getApps().split(",");
				List<Cond> conds = new ArrayList<>();
				for (String s : split) {
					conds.add(new Cond("app", s));
				}
				conds.add(new Cond("language", audience.getLanguage()));
				conds.add(new Cond("country", audience.getCountrys()));
				int[] search = boolQuery.query(conds.toArray(new Cond[0]), index);
				//int[] search = build.search(conds.toArray(new Cond[0]));
				if (search.length != 0) {
					System.out.println("有数据了");
					System.out.println(System.currentTimeMillis() - n);
					//System.out.println(Arrays.toString(search));
				}
				// System.out.println(System.currentTimeMillis()-n);
				// System.out.println(Arrays.toString(search));
			}
		}
	}

	public static void main(String[] args) {
		MainTest mainTest = new MainTest();
		// 建立索引
		mainTest.index();
		// 查询
		//mainTest.query();
	}
}
