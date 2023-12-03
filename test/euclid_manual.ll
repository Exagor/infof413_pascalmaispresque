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
        %a = call i32 @readInt()
        %b = call i32 @readInt()

        ; while 0 < b do
        br label %while.cond
    
    while.cond:
        %0 = icmp slt i32 0, %b
        br i1 %0, label %while.end, label %while.body

    while.body:
        %1 = alloca i32 ; allocate memory for variable b
        store i32 %b, i32* %1 ; store value of b in allocated memory
        %c = load i32, i32* %1 ; load value of b from allocated memory
        br label %while.cond2 
    
    while.cond2:
        ; b < a+1
        %2 = load i32, i32* %a
        %3 = add i32 %2, 1
        %4 = icmp slt i32 %b, %3
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
        %9 = load i32, i32* %a
        store i32 %9, i32* %b
        %10 = load i32, i32* %c
        store i32 %10, i32* %a
        br label %while.cond
    while.end:
        ;print(a)
        %11 = load i32, i32* %a
        call void @println(i32 %11)
        ret i32 0      
}



