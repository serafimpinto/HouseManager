package leituras.impl;

import leituras.service.LeiturasService;

public class LeiturasImpl implements LeiturasService {
	private static final int capacity = 10;
	
	public double getMedia(Double[] leituras ) {
		int count = 0;
		for(int i = 0; i < capacity; i++) {
			if(leituras[i] != null)
				count++;
		}
		double media = 0;
		for(int i = 0; i < count; i++) {
			media = media + leituras[i];
		}
		media = media/count;
		
		return media;		
	}
}
