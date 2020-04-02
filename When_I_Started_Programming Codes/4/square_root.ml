let root x = sqrt (x) ;;





let rec square_root1 x z y =
let var1 = (y +. x/.y)/. 2.0 in    
let var2 = ( var1 +. x/.(var1) /. 2.0) in  

if ( var1 = var2) then var1 else if (z > 1) then  square_root1 (x) (z-1) (var1)  
 else if  z=1  then var1 else square_root1 x z y    ;;





let square_root x z = square_root1 x z (1.0) ;;
