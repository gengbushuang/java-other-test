package com.intelligent.luncene;

import org.apache.commons.lang3.StringUtils;

class Solution {

	public int singleNumber(int[] nums) {
		for (int i = 0; i < nums.length; i++) {
			boolean b = true;
			for (int j = i+1; j < nums.length; j++) {
				if (nums[i] == nums[j]) {
					b = false;
					int tmp = nums[i + 1];
					nums[i + 1] = nums[j];
					nums[j] = tmp;
					i++;
					break;
				}
			}
			if (b) {
				return nums[i];
			}
		}
		return 0;
	}

	public static void main(String[] args) {
//		int[] i = new int[] { 1, 2, 1, 4, 2 };
//		int j = new Solution().singleNumber(i);
//		System.out.println(j);
		if((!StringUtils.isEmpty("")&&!StringUtils.isEmpty(""))||(!StringUtils.isEmpty("f")&&!StringUtils.isEmpty("f"))) {
			System.out.println("h");
		}
		int i=0;
        if (3>2 || (i++)>0){
           System. out .println(i);
        }
	}
}