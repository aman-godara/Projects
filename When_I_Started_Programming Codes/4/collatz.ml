 let rec collatz1 x z = if x =1 then z else if (x mod 2 = 0 ) then collatz1 (x/2) (z+1) 
else if (x mod 2 != 0 ) then collatz1 (3*x +1) (z+1) else -1   ;;






let collatz x = collatz1 x 0 ;; 




let rec max_collatz1 n m = if m=1 then n else if (( collatz1 n 0) >= (collatz1 m 0 )) then max_collatz1 n (m-1) 
else if ( collatz1 n 0) < (collatz1 m 0) then max_collatz1 m (n-1) else -1 ;;



 
let max_collatz x = max_collatz1 x (x-1) ;;
