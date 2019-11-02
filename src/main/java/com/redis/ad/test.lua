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
		local tmp= redis.call("SMEMBERS",value..i)
		table.insert(twotable,tmp);
	end
	--条件个数为0
	if(i==0)
	then
		local tmp = redis.call("SMEMBERS",zero)
		table.insert(twotable,tmp);
	end
end
return twotable