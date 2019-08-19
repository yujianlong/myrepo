package cn.stylefeng.guns.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.StrFormatter;
import com.alibaba.fastjson.util.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yujianlong
 * @create 2019-08-19 18:45
 **/
public class ReadPack {
	public static void getXml(Path debPath) throws IOException {
		List<String> datas = Files.readAllLines(debPath);

		datas.stream().forEach(
				line->{
//					if (!StringUtils.isBlank(line)) {
//						System.out.println(line);
//					}

					if (line.startsWith("Package:")) {
						System.out.println("<package>"+line.split(":")[1].trim()+"</package>");
					}
				}

		);
	}

	private static String xmlinfo = "<package>\n" +
			"\t<id>{}</id>\n" +
			"\t<name>{}</name>\n" +
			"\t<version>{}</version>\n" +
			"\t<compatibility>\n" +
			"\t\t<firmware>\n" +
			"\t\t\t<maxiOS>12.2</maxiOS>\n" +
			"\t\t\t<otherVersions>unsupported</otherVersions>\n" +
			"\t\t\t<!--\n" +
			"\t\t\tfor otherVersions, you can put either unsupported or unconfirmed\n" +
			"\t\t\t-->\n" +
			"\t\t</firmware>\n" +
			"\t</compatibility>\n" +
			"\t<dependencies></dependencies>\n" +
			"\t<descriptionlist>\n" +
			"\t\t<description>{}</description>\n" +
			"\t</descriptionlist>\n" +
			"\t<screenshots></screenshots>\n" +
			"\t<changelog>\n" +
			"\t\t<change>初始化</change>\n" +
			"\t</changelog>\n" +
			"\t<links></links>\n" +
			"</package>";


	private static String xmlchl = "<changelog>\n" +
			"\t<changes>\n" +
			"\t\t<version>{}</version>\n" +
			"\t\t<change>初始化</change>\n" +
			"\t</changes>\n" +
			"</changelog>\n";


	public static void getPV(Path debPath) throws IOException {
		List<String> datas = Files.readAllLines(debPath);
		HashMap<String, String> pvMap = new HashMap<>();

		String baseDir = "/Users/yujianlong/Downloads/pkdir/";

		String pack="";
		String ver="";
		String name="";
		for (int i = 0; i < datas.size(); i++) {
			String line = datas.get(i);
			if (line.startsWith("Package:")) {
				pack = line.split(":")[1].trim();
			}
			if (line.startsWith("Version:")) {
				ver = line.split(":")[1].trim();
			}
			if (line.startsWith("Name:")) {
				name = line.split(":")[1].trim();
			}

			if (StringUtils.isBlank(line)&&StringUtils.isNotEmpty(pack)&&StringUtils.isNotEmpty(ver)&&StringUtils.isNotEmpty(name)) {
				System.out.println(pack+":"+ver+":"+name);

				String ditnew = baseDir + pack;
				FileUtil.mkdir(ditnew);
				//新建文件夹

				String xmlinfo1=StrFormatter.format(xmlinfo, pack, name, ver, name);
				String xmlchl1=StrFormatter.format(xmlchl,ver);
//				System.out.println(xmlinfo1);
				FileUtil.writeString(xmlinfo1, ditnew + "/info.xml","utf-8");
				FileUtil.writeString(xmlchl1, ditnew + "/changelog.xml","utf-8");
				//写入xml

//				break;
			}



		}

	}



	public static void main(String[] args) throws IOException {
		Path debPath = Paths.get("/Users/yujianlong/Downloads/myrepo/Packages");
		List<String> datas = Files.readAllLines(debPath);
		getPV(debPath);
//		getXml(debPath);



	}
}
