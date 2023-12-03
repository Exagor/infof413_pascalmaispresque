@.strR = private unnamed_addr constant [3 x i8] c"%d\00", align 1

define i32 @readInt() {
  %x = alloca i32, align 4
  %1 = call i32 (i8*, ...) @__isoc99_scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.strR, i32 0, i32 0), i32* %x)
  %2 = load i32, i32* %x, align 4
  ret i32 %2
}

declare i32 @__isoc99_scanf(i8*, ...)

@.strP = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

define void @println(i32 %x) {
  %1 = alloca i32, align 4
  store i32 %x, i32* %1, align 4
  %2 = load i32, i32* %1, align 4
  %3 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.strP, i32 0, i32 0), i32 %2)
  ret void
}


declare i32 @printf(i8*, ...)



define i32 @main() {
    entry:
        %x = call i32 @readInt()
        %y = call i32 @readInt()

        %a = alloca i32 ; allocate memory for variable a
        store i32 %x, i32* %a ; store value of x in allocated memory
        %b = alloca i32 ; allocate memory for variable b
        store i32 %y, i32* %b ; store value of y in allocated memory

        ; while 0 < b do
        br label %while.cond
    
    while.cond:

        %z = load i32, i32* %b
        %0 = icmp slt i32 0, %z
        br i1 %0, label %while.end, label %while.body

    while.body:
        %1 = load i32, i32* %b ; allocate memory for variable b
        %c = alloca i32 ; allocate memory for variable c
        store i32 %1, i32* %c ; store value of b in allocated memory

        
        br label %while.cond2 
    
    while.cond2:
        ; b < a+1
        %2 = load i32, i32* %a
        %3 = add i32 %2, 1
        %m = load i32, i32* %b
        %4 = icmp slt i32 %m, %3
        br i1 %4, label %while.end2, label %while.body2
    
    while.body2:
        ; a := a-b...
        %5 = load i32, i32* %a
        %6 = load i32, i32* %b
        %7 = sub i32 %5, %6
        store i32 %7, i32* %a
        br label %while.cond2
    while.end2:
        ;b := a...
        ;a := c
        %8 = load i32, i32* %a
        store i32 %8, i32* %b
        %9 = load i32, i32* %c
        store i32 %9, i32* %a
        br label %while.cond
    while.end:
        ;print(a)
        %10 = load i32, i32* %a
        call void @println(i32 %10)
        ret i32 0      
}



