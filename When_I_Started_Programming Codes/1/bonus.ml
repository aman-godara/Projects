let rec coinChanger1 n a b c d = if (a = n && n!=0) then 1 else if (n<= 0) then 0 else if (a=0) then 0 else (coinChanger1 (n-a) a b c d ) + 
(coinChanger1 n b c d 0)  ;; 
let coinChanger n a b c d = if (n >0 && a >0 && b > 0 && c > 0 && d > 0 ) then coinChanger1 n a b c d else -1 ;;




let test_coin_chng n a b c d = if (coinChanger n a b c d ) > 0 then true else false ;;

let rec coinChanger_cos n a b c d f = if (n-a) = 0 then f(a) else if a = 0 then 0 else if (( test_coin_chng (n-a) a b c d ) = true ) then f(a) + 
(coinChanger_cos (n-a) a b c d f )   else 
coinChanger_cos n b c d 0 f ;;
let coinChanger_cost n a b c d f = if (n > 0 && a > 0 && b > 0 && c > 0 && d > 0) then (coinChanger_cos n a b c d f) else -1 ;; 

 