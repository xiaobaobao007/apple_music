package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Music;
import com.example.demo.entity.suggest;
import com.example.demo.reponsitory.SugRepository;
import com.example.demo.reponsitory.UsRepository;
import com.example.demo.Utils.FileUtil;
import com.example.demo.entity.Classs;
import com.example.demo.entity.FKUser;
import com.example.demo.entity.Matchmusic;
import com.example.demo.service.ClassService;
import com.example.demo.service.MusicService;
import com.example.demo.service.SugService;

@Controller
public class MusicController {
	
	@Resource
	private MusicService musicService;
	@Resource
	private ClassService classService;
	@Resource
	private UsRepository usRepository;
	@Resource
	private SugService sugService;
	@Resource
	private SugRepository sugRepository;
	@Resource
	private FileUtil fileUtil;
	@GetMapping("/")
	public String index() {
		return "redirect:/getStusByPage?pageIndex=1&pageSize=5";
	}
	@GetMapping("/test2")
	public String test2() {
		return "test";
	}
	@GetMapping("/getStusByPage")
	public String getStusByPage(Music music,Matchmusic matchmusic,int pageIndex , int pageSize 
			,WebRequest webRequest,Model model) {
		String user=getUsername();
		String role=getAuthority();
		List<Music> musicnum=musicService.findAll();
		Page<Music> musics= musicService.getMusicByPage(music,matchmusic,pageIndex , pageSize);
		musics=collectmusic(musics,usRepository.findByUsername(user).getMusics());
		int downloadMusic=usRepository.findByUsername(user).getDownloadnums();
		suggest peo=sugRepository.findByid(1);
		int peop=Integer.valueOf(peo.getTel());
		peo.setTel(String.valueOf(peop+1));
		sugRepository.saveAndFlush(peo);
		webRequest.setAttribute("downloadMusic",downloadMusic,RequestAttributes.SCOPE_SESSION);
		webRequest.setAttribute("Objects",musics,RequestAttributes.SCOPE_SESSION);
		webRequest.setAttribute("user", user,RequestAttributes.SCOPE_SESSION);
		webRequest.setAttribute("role", role,RequestAttributes.SCOPE_SESSION);
		List<Classs> musicclass = classService.findAll();
		webRequest.setAttribute("musicclass", musicclass, RequestAttributes.SCOPE_SESSION);
		if (musicnum.size() >= 100) {
			webRequest.setAttribute("disabled_add", true, RequestAttributes.SCOPE_SESSION);
		} else {
			webRequest.setAttribute("disabled_add", false, RequestAttributes.SCOPE_SESSION);
		}
		model.addAttribute("success","欢迎！！欢迎！！您是《第"+peop+"位》光临本系统的贵人");
		return "other/musicList";
	}
	@GetMapping("/getcloectmusic")
	public String getcloectmusic(Music music,Matchmusic matchmusic,int pageIndex , int pageSize 
			,WebRequest webRequest,Model model) {
		String user=getUsername();
		String role=getAuthority();
		List<Music> musicnum=musicService.findAll();
		List<Music> musics=usRepository.findByUsername(user).getMusics();
		webRequest.setAttribute("musics",musics,RequestAttributes.SCOPE_SESSION);
		webRequest.setAttribute("user", user,RequestAttributes.SCOPE_SESSION);
		webRequest.setAttribute("role", role,RequestAttributes.SCOPE_SESSION);
		List<Classs> musicclass = classService.findAll();
		webRequest.setAttribute("musicclass", musicclass, RequestAttributes.SCOPE_SESSION);
		if (musicnum.size() >= 100) {
			webRequest.setAttribute("disabled_add", true, RequestAttributes.SCOPE_SESSION);
		} else {
			webRequest.setAttribute("disabled_add", false, RequestAttributes.SCOPE_SESSION);
		}
		model.addAttribute("success","欢迎！！欢迎！！");
		return "other/musicCloect";
	}
	
	@PostMapping("/suggest")
	public String suggest(@ModelAttribute suggest suggest,WebRequest webRequest,Model model) {
		sugService.save(suggest);
		model.addAttribute("success","意见提交成功！！！感谢支持");
		return "other/musicList";
	}
	
	private Page<Music> collectmusic(Page<Music> music1,List<Music> music2) {
		// 从SecurityContex中获得Authentication对象代表当前用户的信息
		for(int i=0;i<music1.getContent().size();i++){
			music1.getContent().get(i).setClloect(0);
		}
		for(int i=0;i<music1.getContent().size();i++){
			for(int j=0;j<music2.size();j++){
				if(music1.getContent().get(i).getNumber()==music2.get(j).getNumber()){
					System.out.println(i+"|"+j);
					music1.getContent().get(i).setClloect(1);
				}
			}
		}
		return music1;
	}
	


	
	@GetMapping("/UrlTransit/{url}/")
	public String UrlTransit(@PathVariable String url) {
		
		return "MusicPlay";
	}
	@PostMapping("/uploadMusic")
	public String uploadMusic(@ModelAttribute Classs classs,@ModelAttribute Music music, WebRequest webRequest, @RequestParam("file") MultipartFile[] files,
			Model model) throws Exception {
		return fileUtil.uploadMusic(classs,music, webRequest, files, model);
	}
	@PostMapping("/updateMusic")
	public String updateMusic(@ModelAttribute Classs classs,@ModelAttribute Music music, WebRequest webRequest, @RequestParam("file") MultipartFile[] files,
			Model model) throws Exception {
		return fileUtil.updateMusic(classs,music, webRequest, files, model);
	}
	@GetMapping("/musicDetails/{musicsrc}")
	public String gotoBookDetails(@PathVariable String musicsrc, WebRequest webRequest) {
		webRequest.setAttribute("musicsrc", musicsrc, RequestAttributes.SCOPE_REQUEST);
		return "MusicPlay";
	}
	@GetMapping("/deleteByNumber")
	public String delateById(int number,Model model, WebRequest webRequest) {
		
		musicService.deleteByNumber(number);
		Sort sort=new Sort(Sort.Direction.DESC,"clicks");
		Pageable page=PageRequest.of(0,5,sort);
		Page<Music> musics=musicService.findAll(page);
		musics=collectmusic(musics,usRepository.findByUsername(getUsername()).getMusics());
		webRequest.setAttribute("Objects", musics, RequestAttributes.SCOPE_SESSION);
		model.addAttribute("success","您选择编号："+number+",已经被成功删除。");
		return "other/musicList";
	}
	@GetMapping("/findMusicByClasss")
	public String findMusicByClasss(int cord, WebRequest webRequest,Model model) {
		Classs classs=classService.findByCode(cord);
		Sort sort=new Sort(Sort.Direction.DESC,"clicks");
		Pageable page=PageRequest.of(0,10,sort);
		Page<Music> musics=musicService.findByClasss(classs,page);
		webRequest.setAttribute("musics", musics, RequestAttributes.SCOPE_SESSION);
		model.addAttribute("success","您所查询的："+classs.getClassname()+"，查询成功。");
		return "other/musicList";
	}
	
	@ResponseBody//json数据处理方式
	@GetMapping("/updateMusic_clicks")
	public void updateMusic_clicksssssss(String numbe) {
		musicService.updateClicks(Integer.parseInt(numbe));
	}
	@GetMapping("/updateMusic_cloect")
	public void updateMusic_cloect(String number) {
//		System.out.println("123");
//		int index=Integer.parseInt(number.substring(10, number.length()-1));
		int index=Integer.parseInt(number);
		String u=getUsername();
		FKUser user=usRepository.findByUsername(u);
		List<Music> musics=user.getMusics();
		int a=0;
		for(Music music:musics){
			if(music.getNumber()==index){
				musics.remove(music);
				System.out.println("取消收藏");
				a=1;
				break;
			}
		}
		if(a==0) {
			Music musicone=musicService.findByNumber(index);
			musics.add(musicone);
			System.out.println("添加收藏");
		}
		user.setMusics(musics);
		usRepository.saveAndFlush(user);
	}
	
	@GetMapping("/downloadmusic")
	public ResponseEntity<byte[]> downloadmusic(String filename,
			@RequestHeader("User-Agent") String UserAgent) throws Exception {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		FKUser user=usRepository.findByUsername(username);
		int nums=user.getDownloadnums();
		user.setDownloadnums(nums-1);
		System.out.println(nums);
		usRepository.saveAndFlush(user);
		return new FileUtil().downloadMusic(filename, UserAgent);
	}
	
	private String getUsername() {
		// 从SecurityContex中获得Authentication对象代表当前用户的信息
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println("username = " + username);
		return username;
	}
	/**
	 * 获得当前用户权限
	 */
	private String getAuthority() {
		// 获得Authentication对象，表示用户认证信息。
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<String> roles = new ArrayList<String>();
		// 将角色名称添加到List集合
		for (GrantedAuthority a : authentication.getAuthorities()) {
			roles.add(a.getAuthority());
		}
		System.out.println("role = " + roles);
		return roles.toString();
	}
	
	
	
}