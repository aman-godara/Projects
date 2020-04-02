module MyList = struct


exception Failure of string ;; 

exception Invalid_argument of string ;;




let rec length l = match l with 
| hd :: tl -> 1 + length (tl) 
| [] -> 0 ;;



let rec nth l a = match l with 
| hd :: tl -> if (a<0 ) then raise (Failure "nth") else if (a >= length l ) then
 raise (Invalid_argument "List.nth") 
else if (a = 0) then hd else nth (tl) (a-1) 
| [] -> -1;;



let rec flatten l = 
if l = [] then [] else 
let hd :: tl = l in 

let rec find bottle = match bottle with

| a :: b -> a :: ( find b ) 

| [] -> flatten(tl)   in 
find (hd) ;;




let rec map l f = match l with 

| [] -> [] 

| hd :: tl -> (f (hd)) :: map (tl) f  ;;


let rec rev1 l pt  = match l with 

| ab::cd ::[] -> cd::ab::pt

|hd :: tl:: rest ->  rev1 (rest) (tl :: hd :: pt)

|nr ::[] -> nr :: pt  

|[] -> [] ;;



let rev l = rev1 l [] ;;




let rec map l f = match l with 

|[] -> [] 

| hd :: tl -> (f (hd)) :: map (tl) f  ;;








let rev_map f l  = (map (rev l) f) ;; 


let rec fold_left f a l = match l with 
| hd :: tl  -> let t = (f a hd ) in fold_left f t (tl)
|  [] -> a;; 


let rec fold_right f l b  = match l with 
|hd :: [] -> f (hd) b  
| hd :: tl -> f (hd) (fold_right f (tl) b ) ;;

let rec book1 l = match l with 
|hd :: tl -> hd ;;

let rec book2 l = match l with
| hd :: tl -> tl ;; 

let rec map2 f l1 l2 = if (length l1 != length l2) then raise Invalid_argument else if (l1 != [] && l2 != [] ) 
then f (book1 l1) (book1 l2) :: map2 f (book2 l1) (book2 l2)  else  [] ;; 

exception Invalid_argument  ;;

let rec length l =
match l with
| [] -> 0
| h::t -> 1 + length t ;;

let book1 l = match l with 
| hd :: tl  -> hd ;;


let book2 l = match l with 
| hd :: tl -> tl ;;

let rec fold_left2 f a l1 l2 =  
	if ((length l1) != (length l2)) then raise Invalid_argument else if l1 = [] then a 
else let var1 = f a (book1 l1) (book1 l2) in 
fold_left2 f var1 (book2 l1) (book2 l2)   ;;

let rec for_all f l = match l with 
| hd :: tl :: rest -> if (f hd = true && f tl = true) then for_all f rest else false 
| hd :: [] -> if f hd = true then true else false
| [] -> false ;;  


let rec exists f l = match l with
| hd :: tl :: rest -> if (f hd = true || f tl = true ) then true else exists f rest 
| hd :: [] -> if f hd = true then true else false ;;


let rec filter f l = match l with 
| hd :: tl -> if ( for_all f [hd] = true ) then hd :: (filter f tl) else filter f (tl) 
|  [] -> [] ;;

end 