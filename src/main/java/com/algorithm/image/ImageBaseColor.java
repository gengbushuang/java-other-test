package com.algorithm.image;

public enum ImageBaseColor{
	RED{
		@Override
		public int getColor(int v) {
			return (v >> 16) & 0xff;
		}
		
	},GREEN{
		@Override
		public int getColor(int v) {
			return (v >> 8) & 0xff;
		}
		
	},BLUE{
		@Override
		public int getColor(int v) {
			return v & 0xff;
		}
	};
	
	public abstract int getColor(int v);
}
