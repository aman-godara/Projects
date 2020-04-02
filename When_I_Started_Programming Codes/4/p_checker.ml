let first x y = if (float_of_int y <= (float_of_int x)**0.5 ) then true else false ;;







let rec isPrime1 x y = let var1 = x mod y in    if (first x y = true && var1 = 0 ) 
then false else if ( var1 != 0 && first x y = true ) then (isPrime1 x (y+1) ) else  true ;;


  

let isPrime x = if x >1 then (isPrime1 x 2) else false ;;
