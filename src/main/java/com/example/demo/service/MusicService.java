package com.example.demo.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Classs;
import com.example.demo.entity.Matchmusic;
import com.example.demo.entity.Music;
import com.example.demo.reponsitory.MusicRepository;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class MusicService {
	@Resource
	private MusicRepository musicRepository;

	@Transactional
	public Music save(Music music) {

		return musicRepository.save(music);
	}

	@Transactional
	public void deleteById(int id) {

		musicRepository.deleteById(id);
	}

	@Transactional
	public void deleteByNumber(int number) {

		musicRepository.deleteByNumber(number);
	}

	@Transactional
	public void updateClicks(int number) {

		Music music = musicRepository.findByNumber(number);
		music.setClicks(music.getClicks() + 1);
		musicRepository.saveAndFlush(music);
	}

	@Transactional
	public void updateOne(int number, Music m) {

		Music music = musicRepository.findByNumber(number);
		music.setName(m.getName());
		music.setSinger(m.getSinger());
		music.setClasss(m.getClasss());
		music.setSrc(m.getSrc());
		music.setClicks(m.getClicks());
		music.setImage(m.getImage());
		musicRepository.saveAndFlush(music);
	}

	@Transactional
	public List<Music> saveAll(Set<Music> musics) {

		return musicRepository.saveAll(musics);
	}

	public Music findByNumber(Integer number) {

		return musicRepository.findByNumber(number);
	}

	public List<Music> findAll() {

		return musicRepository.findAll();
	}

	public int nullfirst() {
		long nums = musicRepository.count();
		for (int i = 1; i <= (int) nums; i++) {
			if (!musicRepository.findById(i).isPresent()) {
				return i;
			}
		}
		return (int) (nums + 1);
	}

	public Page<Music> findAll(Pageable page) {

		return musicRepository.findAll(page);
	}

	public Page<Music> findByClasss(Classs classs, Pageable page) {

		return musicRepository.findByClasss(classs, page);
	}


	public Page<Music> getMusicByPage(Music music, Matchmusic matchmusic, int pageIndex, int pageSize) {

		Sort sort = new Sort(Sort.Direction.DESC, "clicks");
		Specification<Music> musicspe = MusicSpecification.QueryMusic(music, matchmusic);
		Page<Music> pages = musicRepository.findAll(musicspe, PageRequest.of(pageIndex - 1, pageSize, sort));
		return pages;
	}


}