let check_poly x y z = if (x>0 && y>0 && z>0 ) then true else false ;;



let poly x y z = if (check_poly x y z = true) then (x*x*x) + (2*x*y*z*z) - y*z + 1   else -1 ;;



