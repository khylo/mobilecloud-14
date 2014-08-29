package org.magnum.dataup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.magnum.dataup.model.Video;
import org.magnum.dataup.model.VideoRepository;
import org.magnum.dataup.model.VideoStatus;
import org.magnum.dataup.model.VideoStatus.VideoState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedInput;

@Service
public class VideoService {
	@Autowired
	private VideoRepository videoRepo;
	private VideoFileManager videoDataMgr;
	private Logger log = LoggerFactory.getLogger(getClass());

	Random random;
	
	@PostConstruct
	public void init() throws IOException{
		random = new Random();
		videoDataMgr = VideoFileManager.get();
		/*File targetDir = new File(videoDataMgr.getDir());
		if(!targetDir.exists())
			targetDir.mkdir();*/
	}

	public Collection<Video> getVideoList() {
		//System.out.println("Running on port ="+Utils.getPort());
		Iterable<Video> it = videoRepo.findAll();
		if(it==null)
			return null;
		else
			return Lists.newArrayList(it);
	}

	public Video addVideo(Video v) {
		 Video ret = videoRepo.save(v);
		 ret.setDataUrl("localhost:8080");//+Utils.getPort());
		 log.info("Added new video "+Utils.toString(v));
		 return ret;
	}

	public VideoStatus setVideoData(long id,  InputStream videoData) throws IOException{
		Video v = videoRepo.findOne(id);
		if(v==null)
			throw new ResourceNotFoundException("Cannot find video id:"+id);
		String path = videoDataMgr.saveVideoData(v, videoData);
		v.setDataUrl("localhost:8080"+/*Utils.getPort()+*/"/"+path);
		return new VideoStatus(VideoState.READY);
	}

	public void getData(long id, OutputStream out) throws IOException {
		Video v = videoRepo.findOne(id);
		if(v==null)
			throw new ResourceNotFoundException("Cannot find video id:"+id);
		videoDataMgr.copyVideoData(v, out);
	}
	
	private Long getRamdomLong(){
		return random.nextLong();
	}

}
