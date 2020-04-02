let check_nlog x  = if ((x +. sqrt(x**2.0 +. 1.0 )) > 0.0 ) then true else false ;;

let nlog x = if ( check_nlog x = true ) then log(x +. sqrt(x**2.0 +. 1.0)) else -1.0 ;;

