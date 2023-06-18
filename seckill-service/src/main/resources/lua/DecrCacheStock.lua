local key=KEYS[1];
local surplusStock = tonumber(redis.call('get',key));
if (surplusStock<=0) then return 0
else
    redis.call('incrby', key, -1)
    return 2
end