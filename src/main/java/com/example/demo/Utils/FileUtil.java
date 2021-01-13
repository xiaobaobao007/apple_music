package com.example.demo.Utils;

import java.io.File;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Classs;
import com.example.demo.entity.FKUser;
import com.example.demo.entity.Music;
import com.example.demo.reponsitory.UsRepository;
import com.example.demo.service.ClassService;
import com.example.demo.service.MusicService;
import com.example.demo.service.UsService;

@Component
@RestController
public class FileUtil {

	@Resource
	private MusicService musicService;
	@Resource
	private ClassService classService;
	@Resource
	private UsRepository usRepository;

	public String uploadMusic(Classs classs, Music music, WebRequest webRequest, MultipartFile[] files, Model model)
			throws Exception {
		long Maxfile = 4 * 1024 * 1024;
		String a = "音乐文件";
		String filetypes = "mp3";
		for (int i = 0; i <= 1; i++) {
			System.out.println("loading=" + i);
			MultipartFile file = files[i];
			if (i == 1) {
				a = "音乐图片";
				filetypes = "jpg";
			}
			if (!file.isEmpty()) {
				String path = System.getProperty("user.dir");
				path += File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator
						+ "static";
				if (i == 0)
					path += File.separator + "music";
				else
					path += File.separator + "img";
				System.out.println("path=" + path);
				String filename = file.getOriginalFilename();
				filename = filename.substring(filename.lastIndexOf("\\") + 1).toLowerCase().trim();// 获取文件名
				String extname = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase().trim();
				System.out.println(filename + "\n" + extname);
				List<String> filetype = Arrays.asList(filetypes.split(":"));
				if (!(filetype.contains(extname))) {
					String info = "上传的" + a + "必须是" + filetypes;
					model.addAttribute("faild", info);
					return "other/musicList";
				}
				if (file.getSize() > Maxfile) {
					String info = "上传的" + a + "必须小于" + Maxfile / 1024 / 1024 + "Mb";
					model.addAttribute("faild", info);
					return "other/musicList";
				}
				File filepath = new File(path, filename);
				if (!filepath.getParentFile().exists()) {
					filepath.getParentFile().mkdirs();
				}
				file.transferTo(new File(path + File.separator + filename));
			} else {
				model.addAttribute("faild", a + "上传失败");
				return "other/musicList";
			}
			System.out.println("loaded:" + i);
		}
		System.out.println("loaded:" + music);
		String src = music.getSrc().substring(music.getSrc().lastIndexOf("\\") + 1).toLowerCase().trim();
		String image = music.getImage().substring(music.getImage().lastIndexOf("\\") + 1).toLowerCase().trim();
		music.setId(musicService.nullfirst());
		music.setSrc(src);
		music.setImage(image);
		if(classService.findByClassname(classs.getClassname()) != null){
			
			music.setClasss(classService.findByClassname(classs.getClassname()));
		}
		else{
			
			Classs cl=new Classs(classs.getClassname());
			classService.save(cl);
			music.setClasss(cl);
			List<Classs> musicclass = classService.findAll();
			webRequest.setAttribute("musicclass", musicclass, RequestAttributes.SCOPE_SESSION);
		}
		musicService.save(music);
		Sort sort = new Sort(Sort.Direction.DESC,"clicks");
		Pageable page = PageRequest.of(0, 5, sort);
		Page<Music> musics = musicService.findAll(page);
		webRequest.setAttribute("Objects", musics, RequestAttributes.SCOPE_SESSION);
		model.addAttribute("success", "上传成功");
		return "other/musicList";
	}

	public String updateMusic(Classs classs, Music music, WebRequest webRequest, MultipartFile[] files, Model model)
			throws Exception {
		long Maxfile = 4 * 1024 * 1024;
		String a = "音乐文件";
		String filetypes = "mp3";
		for (int i = 0; i <= 1; i++) {
			System.out.println("loading=" + i);
			if (i == 0) {
				System.out.println(music.getSrc());
				System.out.println(musicService.findByNumber(music.getNumber()).getSrc());
				if (music.getSrc().equals(musicService.findByNumber(music.getNumber()).getSrc())) {
					continue;
				}
			} else {
				System.out.println(music.getImage());
				System.out.println(musicService.findByNumber(music.getNumber()).getImage());
				if (music.getImage().equals(musicService.findByNumber(music.getNumber()).getImage())) {
					continue;
				}
			}
			MultipartFile file = files[i];
			if (i == 1) {
				a = "音乐图片";
				filetypes = "jpg";
			}
			if (!file.isEmpty()) {
				String path = System.getProperty("user.dir");
				path += File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator
						+ "static";
				if (i == 0)
					path += File.separator + "music";
				else
					path += File.separator + "img";
				System.out.println("path=" + path);
				String filename = file.getOriginalFilename();
				filename = filename.substring(filename.lastIndexOf("\\") + 1).toLowerCase().trim();// 获取文件名
				String extname = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase().trim();
				System.out.println(filename + "\n" + extname);
				List<String> filetype = Arrays.asList(filetypes.split(":"));
				if (!(filetype.contains(extname))) {
					String info = "上传的" + a + "必须是" + filetypes;
					model.addAttribute("faild", info);
					return "other/musicList";
				}
				if (file.getSize() > Maxfile) {
					String info = "上传的" + a + "必须小于" + Maxfile / 1024 / 1024 + "Mb";
					model.addAttribute("faild", info);
					return "other/musicList";
				}
				File filepath = new File(path, filename);
				if (!filepath.getParentFile().exists()) {
					filepath.getParentFile().mkdirs();
				}
				file.transferTo(new File(path + File.separator + filename));
			} else {
				model.addAttribute("faild", a + "上传失败");
				return "other/musicList";
			}
			System.out.println("loaded:" + i);
		}
		System.out.println("loaded:" + music);
		music.setClasss(classService.findByClassname(classs.getClassname()));
		musicService.updateOne(music.getNumber(), music);
		Sort sort = new Sort(Sort.Direction.DESC,"clicks");
		Pageable page = PageRequest.of(0, 5, sort);
		Page<Music> musics = musicService.findAll(page);
		webRequest.setAttribute("Objects", musics, RequestAttributes.SCOPE_SESSION);
		model.addAttribute("success", "更新成功");
		return "other/musicList";
	}


	public ResponseEntity<byte[]> downloadImg(String filename, String UserAgent) throws Exception {

		// C:\Users\Administrator\Desktop
		// C:\Users\Administrator\Desktop\music\src\main\resources\static\music
		// 下载文件路径
		String path = System.getProperty("user.dir");
		path += File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator
				+ "static" + File.separator + "userinfo_img";
		// String path = request.getServletContext().getRealPath("/upload/");
		/*
		 * String path = "C:" + File.separator + "Users" +File.separator +
		 * "Administrator" +File.separator + "Desktop"+File.separator + "下载路径";
		 */
		//

		File file = new File(path + File.separator + filename);
		// ok表示Http中的状态200
		BodyBuilder builder = ResponseEntity.ok();
		// 内容长度
		builder.contentLength(file.length());
		builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
		// 使用URLEncoder.encode对文件进行解码
		filename = URLEncoder.encode(filename, "UTF-8");
		// 设置实际的响应文件名，告诉浏览器文件要用于“下载”和“保存”
		// 不同的浏览器，处理方式不同，要根据浏览器版本区别对待
		if (UserAgent.indexOf("MSIE") > 0) {
			// 如果是IE,只需要用UTF-8字符集进行URL编码即可
			builder.header("Content-Disposition", "attachment;filename" + filename);
		} else {
			// 二Firefox，Chrome等浏览器，则需要说明编码的字符集
			// 注意filename后面有个*号，在UTF-8后面有两个单引号
			builder.header("Content-Disposition", "attachment;filename*=UTF-8''" + filename);

		}

		return builder.body(FileUtils.readFileToByteArray(file));

	}

	public ResponseEntity<byte[]> downloadMusic(String filename, String UserAgent) throws Exception {

		String path = System.getProperty("user.dir");
		path += File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator
				+ "static" + File.separator + "music";

		File file = new File(path + File.separator + filename);
		// ok表示Http中的状态200
		BodyBuilder builder = ResponseEntity.ok();
		// 内容长度
		builder.contentLength(file.length());
		// APPLICATION_OCTET_STREAM:二进制流数据（最常用的文件下载）
		builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
		// 使用URLEncoder.encode对文件进行解码
		filename = URLEncoder.encode(filename, "UTF-8");
		// 设置实际的响应文件名，告诉浏览器文件要用于“下载”和“保存”
		// 不同的浏览器，处理方式不同，要根据浏览器版本区别对待
		if (UserAgent.indexOf("MSIE") > 0) {
			// 如果是IE,只需要用UTF-8字符集进行URL编码即可
			builder.header("Content-Disposition", "attachment;filename" + filename);
		} else {
			// 二Firefox，Chrome等浏览器，则需要说明编码的字符集
			// 注意filename后面有个*号，在UTF-8后面有两个单引号
			builder.header("Content-Disposition", "attachment;filename*=UTF-8''" + filename);

		}

		return builder.body(FileUtils.readFileToByteArray(file));

	}

	@GetMapping(value = "/export")
	public void export(WebRequest webRequest, HttpServletResponse response) throws Exception {
		// 获取数据
		List<Music> musics = musicService.findAll();

		// excel标题
		String[] title = { "编号", "名称", "歌手", "路径", "点击量", "图片" };

		// excel文件名
		String fileName = "music_" + System.currentTimeMillis() + ".xls";

		// sheet名
		String sheetName = "音乐信息表";
		String[][] content = new String[musics.size()][6];
		for (int i = 0; i < musics.size(); i++) {
			Music obj = musics.get(i);
			content[i][0] = Integer.toString(obj.getNumber());
			content[i][1] = obj.getName();
			content[i][2] = obj.getSinger();
			content[i][3] = obj.getSrc();
			content[i][4] = Integer.toString(obj.getClicks());
			content[i][5] = obj.getImage();
		}

		// 创建HSSFWorkbook
		HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

		// 响应到客户端
		try {
			this.setResponseHeader(response, fileName);
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 发送响应流方法
	public void setResponseHeader(HttpServletResponse response, String fileName) {
		try {
			try {
				fileName = new String(fileName.getBytes(), "UTF8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// response.setContentType("application/octet-stream;charset=ISO8859-1");
			// response.setHeader("Content-Disposition", "attachment;filename="+
			// fileName);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");// 这里设置一下让浏览器弹出下载提示框，而不是直接在浏览器中打开
			// response.addHeader("Pargam", "no-cache");
			// response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
