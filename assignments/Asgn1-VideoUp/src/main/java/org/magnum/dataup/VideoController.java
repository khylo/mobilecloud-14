/*
 * 
 * Copyright 2014 Jules White
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.magnum.dataup;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.magnum.dataup.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import retrofit.client.Response;

@RestController
public class VideoController {
	
	@Autowired
	VideoService videoService;
	
	@PostConstruct
	public void init(){
		
	}

	/**
	 * You will need to create one or more Spring controllers to fulfill the
	 * requirements of the assignment. If you use this file, please rename it
	 * to something other than "AnEmptyController"
	 * 
	 * 
		 ________  ________  ________  ________          ___       ___  ___  ________  ___  __       
		|\   ____\|\   __  \|\   __  \|\   ___ \        |\  \     |\  \|\  \|\   ____\|\  \|\  \     
		\ \  \___|\ \  \|\  \ \  \|\  \ \  \_|\ \       \ \  \    \ \  \\\  \ \  \___|\ \  \/  /|_   
		 \ \  \  __\ \  \\\  \ \  \\\  \ \  \ \\ \       \ \  \    \ \  \\\  \ \  \    \ \   ___  \  
		  \ \  \|\  \ \  \\\  \ \  \\\  \ \  \_\\ \       \ \  \____\ \  \\\  \ \  \____\ \  \\ \  \ 
		   \ \_______\ \_______\ \_______\ \_______\       \ \_______\ \_______\ \_______\ \__\\ \__\
		    \|_______|\|_______|\|_______|\|_______|        \|_______|\|_______|\|_______|\|__| \|__|
                                                                                                                                                                                                                                                                        
	 * 
	 */
	@RequestMapping(method=RequestMethod.GET, value=VideoSvcApi.VIDEO_SVC_PATH)
	@ResponseBody
	public Collection<Video> getVideos(){
		return videoService.getVideoList();
	}
	
	@RequestMapping(method=RequestMethod.POST, value=VideoSvcApi.VIDEO_SVC_PATH)
	@ResponseBody
	public Video addVideo(Video v){
		return videoService.addVideo(v);
	}
	
	@RequestMapping(method=RequestMethod.GET, value=VideoSvcApi.VIDEO_DATA_PATH)
	public void getVideoData(@PathVariable("id") long id, HttpServletResponse resp) throws IOException{
		videoService.getData(id, resp.getOutputStream());		
	}
	
	@RequestMapping(method=RequestMethod.POST, value=VideoSvcApi.VIDEO_DATA_PATH)
	public void setVideoData(@PathVariable("id") long id, MultipartFile videoData) throws IOException {
		videoService.setVideoData(id, videoData.getInputStream());
 	}
	
}
