local nums = ARGV[1]
local zero = ARGV[2]
local szSeparator = ARGV[3]
local keys = KEYS
local twotable = {}

for i=0,nums
do
	local array= {}
	--循环条件个数
	for key,value in ipairs(keys)
	do
		--判断参数是否带多个字段
		local szFullString = value
		local nFindStartIndex = 1
		local nSplitIndex = 1
		local nSplitArray = {}
		while true do
	   		local nFindLastIndex = string.find(szFullString, szSeparator, nFindStartIndex)
	   		if not nFindLastIndex then
	    		nSplitArray[nSplitIndex] = string.sub(szFullString, nFindStartIndex, string.len(szFullString))..i
	    		break
	   		end
	   		nSplitArray[nSplitIndex] = string.sub(value, nFindStartIndex, nFindLastIndex - 1)..i
	   		nFindStartIndex = nFindLastIndex + string.len(szSeparator)
	   		nSplitIndex = nSplitIndex + 1
		end
		
		local tmp
		if nSplitIndex==1 then
			tmp = redis.call("SMEMBERS",nSplitArray[nSplitIndex])
			--table.insert(array,nSplitArray)
		else
			tmp = redis.call("SUNION",unpack(nSplitArray))
			--table.insert(array,unpack(nSplitArray))
		end
		
		for tmp_k,tmp_v in ipairs(tmp)
		do
			table.insert(array,tmp_v)
		end
		--table.insert(array,tmp)
		--table.insert(array,list)
	end
	--条件个数为0
	if(i==0)
	then
		local tmp = redis.call("SMEMBERS",zero)
		for tmp_k,tmp_v in ipairs(tmp)
		do
			table.insert(array,tmp_v)
		end
		--table.insert(array,tmp)
		--table.insert(array,zero)
	end
	
	table.insert(twotable,array);
end
return twotable