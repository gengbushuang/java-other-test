package com.dnf.test;

import java.util.Random;

public class AppSupportMode {

	static final String AD_APP = "test:ad:app:";
	
	String[] str = { "all", "1000497", "1004544", "1002325", "1003201", "1000785", "1003466", "1004591", "1000769",
			"1000588", "1001367", "1000862", "1001482", "1000673", "1000732", "1000357", "1000797", "1000538",
			"1001197", "1001495", "1000415", "1002501", "1000572", "1001876", "1000462", "1001229", "1009528",
			"1002869", "1001578", "1000478", "1002447", "1003069", "1001579", "1000297", "1000888", "1006608",
			"1001157", "1002487", "1002799", "1001738", "1002833", "1000317", "1000402", "1913924", "1000340",
			"1000506", "1000263", "1000040", "1000064", "1000101", "1000339", "1000217", "1000160", "1000184",
			"1000100", "1000071", "1000460", "1000277", "1000201", "1000162", "1000095", "1002365", "1006704",
			"1001560", "1000219", "1001417", "1001195", "1001325", "1001794", "1000566", "1000644", "1000293",
			"1002006", "1000810", "1000620", "1000710", "1001692", "1002776", "1005176", "1001193", "1001184",
			"1002987", "1000637", "1000853", "1000737", "1000476", "1000791", "1000145", "1000338", "1001049",
			"1000168", "1000286", "1000651", "1000409", "1000153", "1000487", "1003670", "1000350", "1000954",
			"1000479", "1001135", "1000365", "1003881", "1005226", "1001319", "1004448", "1002289", "1004213",
			"1008487", "1002870", "1003265", "1002058", "1000232", "1003239", "1006454", "1004167", "1000498",
			"1009985", "1002734", "1002186", "1007822", "1004089", "1000759", "1001377", "1006495", "1002739",
			"1000368", "1000856", "1002189", "1002605", "1001189", "1002944", "1001941", "1000099", "1000156",
			"1000893", "1000923", "1000966", "1002549", "1001804", "1001153", "1001373", "1000097", "1000183",
			"1000413", "1000180", "1000541", "1000697", "1000123", "1000561", "1000224", "1000345", "1000337",
			"1000491", "1000433", "1000844", "1000373", "1000521", "1000659", "1000489", "2303201", "1000438",
			"1000657", "1016689", "1009956", "1001546", "1017870", "1010330", "1009863", "1013251", "1000213",
			"1000724", "1003043", "1014471", "1015283", "1004343", "1026979", "1030964", "1005855", "1004039",
			"1003868", "1012679", "1002090", "1011155", "1004848", "1005879", "1011913", "1007284", "1014022",
			"1011071", "1003514", "1015188", "1003103", "1003202", "1016648", "1011927", "1008821", "1003718",
			"1003707", "1006840", "1012557", "1004893", "1009332", "1001897", "1000558", "1001553", "1000985",
			"1002137", "1000661", "1000432", "1000342", "1000918", "1001148", "1001346", "1000833", "1000900",
			"1001481", "1000446", "1001004", "1001539", "1001048", "1000310", "1000914", "1000163", "1003710",
			"1003961", "1002174", "1004925", "1004191", "1001945", "1003075", "1000752", "1001006", "1001926",
			"1003268", "1004805", "1000304", "1000531", "1000227", "1004854", "1000906", "1001790", "1003256",
			"1000813", "1000693", "1000964", "1000704", "1000305", "1000250", "1000798", "1000863", "1000913",
			"1000503", "1000279", "1000236", "1000624", "1000346", "1001050", "1000596", "1000188", "1000522",
			"1000370", "1000152", "1001858", "1000742", "1001783", "1001311", "1000930", "1000832", "1001261",
			"1003066", "1001267", "1001938", "1001019", "1001072", "1000947", "1002019", "1002073", "1000559",
			"1000733", "1001604", "1002952", "1000728", "1000516", "1001142", "1000136", "1000113", "1000643",
			"1000448", "1000190", "1000821", "1000726", "1000364", "1000336", "1000092", "1000215", "1000897",
			"1000763", "1001030", "1000425", "1000104", "1000344", "1000299", "1000927", "1001040", "1000127",
			"1000006", "1000447", "1000125", "1000467", "1000486", "1000267", "1000043", "1000035", "1000384",
			"1000548", "1000225", "1000456", "1000627", "1000725", "1000323", "1000171", "1000192", "1169971",
			"1156891", "1053048", "1117955", "1089590", "1122702", "1140231", "1124185", "1062377", "1082648",
			"1158762", "1113541", "1170195", "1160350", "1115112", "1064083", "1118840", "1097577", "1180294",
			"1122495", "1000069", "1000048", "1000073", "1000176", "1000216", "1000130", "1000116", "1000134",
			"1000084", "1000093", "1000061", "1000015", "1000182", "1000258", "1000032", "1000105", "1000186",
			"1000088", "1000198", "1000161", "1000089", "1000072", "1000204", "1000083", "1000157", "1000147",
			"1000167", "1000051", "1000191", "1000111", "1000047", "1000233", "1000287", "1000057", "1011581",
			"1000119", "1000326", "1000065", "1000063", "1000085", "1000053", "1000165", "1000189", "1000220",
			"1000386", "1000080", "1000660", "1000493", "1000327", "1000091", "1000296", "1000150", "1000138",
			"1000391", "1000234", "1000444", "1000248", "1000319", "1000245", "1000108", "1000817", "1001983",
			"1001509", "1003536", "1001078", "1001694", "1003971", "1001685", "1003199", "1000962", "1003541",
			"1000534", "1003595", "1004082", "1002499", "1009197", "1003390", "1000937", "1003511", "1003005",
			"1003404", "1005435", "1004865", "1003401", "1002490", "1004166", "1004187", "1002371", "1001955",
			"1005265", "1002567", "1003819", "1003758", "1003443", "1001180", "1001219", "1003549", "1003336",
			"1001295", "1003105", "1009960", "1001832", "1001739", "1001896", "1001015", "1004356", "1001807",
			"1002023", "1002091", "1001204", "1002785", "1003576", "1003894", "1001588", "1000699", "1001382",
			"1003218", "1000355", "1002879", "1002471", "1001881", "1001474", "1002339", "1001979", "1002046",
			"1000468", "1000256", "1000303", "1000240", "1000374", "1000414", "1002275", "1000935", "1001241",
			"1007474", "2266863", "1000055", "1006062", "1000284", "1000477", "1000361", "1000575", "1001726",
			"1004475", "1001861", "1000030", "1000645", "1000466", "1001467", "1001300", "1001086", "1000700",
			"1001512", "1001745", "1001770", "1000527", "1001003", "1000173", "1000709", "1000347", "1000719",
			"1000390", "1001120", "1003113", "1003057", "1001664", "1002802", "1000545", "1001279", "1004496",
			"1001381", "1003479", "1004015", "1004170", "1000691", "1002072", "1002086", "1002968", "1001156",
			"1000664", "1001007", "1000492", "1000682", "1000454", "1000744", "1001551", "1001650", "1001051",
			"1001734", "1001511", "1001636", "1000695", "1001528", "1001138", "1000322", "1001687", "1000983",
			"1001759", "1001137", "1001290", "1000308", "1000636", "1001558", "1001014", "1002623", "1000396",
			"1000803", "1001499", "1000574", "1002761", "1002131", "1002947", "1001463", "1001139", "1000291",
			"1000998", "1001426", "1000866", "1000547", "1001772", "1003495", "1001453", "1000371", "1000933",
			"1000711", "1000740", "1000978", "1001133", "1000640", "1000941", "1000228", "1000124", "1000747",
			"1000488", "1000876", "1000441", "1000407", "1000399", "1001203", "1000788", "1001143", "1000512",
			"1009050", "1008109", "1005697", "1001472", "1013095", "1003743", "1000366", "1003993", "1003464",
			"1003864", "1003395", "1008722", "1001762", "1004254", "1001422", "1005911", "1002412", "1006618",
			"1002368", "1002954", "1000244", "1000148", "1000140", "1000633", "1000002", "1000020", "1000049",
			"1000011", "1000369", "1000181", "1000158", "1000268", "1000203", "1000045", "1000055", "2266863",
			"1000036", "1000381", "1000502", "1000066", "1000290", "1000520", "1000199", "1000670", "1001012",
			"1000739", "1000481", "1000569", "1000353", "1000135", "1000589", "1000388", "1000421", "1000738",
			"1000663", "1000434", "1000320", "1000196", "1000393", "1000525", "1000431", "1002057", "1001248",
			"1000940", "1000208", "1001425", "1001025", "1000359", "1001105", "1001366", "1000614", "1001798",
			"1001094", "1001715", "1001552", "1000262", "1000029", "1000076", "1002893", "1001270", "1000511",
			"1000395", "1000989", "1000112", "1000254", "1000275", "1000543", "1000430", "1000280", "1000056",
			"1579034", "1000075", "1813978", "1000610", "1000385", "1000536", "1000375", "1000315", "1000247",
			"1000273", "1000428", "1000667", "1000175", "1000847", "1002005", "1002908", "1003793", "1003141",
			"1002886", "1005184", "1003277", "1000686", "1000552", "1000515", "1002291", "1001961", "1002205",
			"1002126", "1002110", "1002272", "1010472", "1004065", "1005982", "1000380", "1001024", "1000626",
			"1000939", "1000472", "1000379", "1001062", "1000843", "1000436", "1000429", "1000873", "1001047",
			"1000921", "1000784", "1000892", "1000793", "1000895", "1001125", "1000253", "1000802", "1000174",
			"1000274", "1000313", "1000601", "1000580", "1000464", "1000197", "1000599", "1000249", "1000285",
			"1000650", "1000578", "1000334", "1000500", "1000283", "1000412", "1000480", "1001037", "1000255",
			"1000289", "1001870", "1001028", "1000490", "1001757", "1001441", "1001865", "1000458", "1001281",
			"1001659", "1001862", "1001411", "1002984", "1000678", "1002150", "1000579", "1002238", "1000745",
			"1000622", "1001665", "1002629", "1077095", "1023915", "1020540", "1051364", "1009357", "1027900",
			"1050992", "1083315", "1028114", "1016644", "1030317", "1032802", "1057763", "1019002", "1069783",
			"1034301", "1070702", "1053293", "1010421", "1032752", "1000012", "1000062", "1000018", "1028447",
			"1000008", "1000027", "1000074", "1000042", "1000067", "1000054", "1000034", "1000070", "1000050",
			"1000106", "1000044", "1000022", "1000037", "1000001", "1000068", "1000017", "1000060", "1000979",
			"1003978", "1004180", "1002757", "1001245", "1004951", "1002940", "1007428", "1005530", "1005987",
			"1009228", "1003545", "1004537", "1001420", "1004042", "1002994", "1005398", "1004890", "1004057",
			"1004888", "1000144", "1000912", "1000356", "1000021", "1000411", "1000401", "1000246", "1000508",
			"1000031", "1000207", "1000179", "1000126", "1000211", "1000129", "1000298", "1000226", "1000121",
			"1000453", "1000238", "1000295" };

	Random random = new Random();

	public String getValue() {
		int nextInt = random.nextInt(str.length);
		return AD_APP+str[nextInt];
	}
	
	public String getV() {
		int nextInt = random.nextInt(str.length);
		return str[nextInt];
	}
}
