public enum State {
    Program,
    Code,
    InstList,
    Instruction_end,
    Instruction,
    Assign,
    ExprArith,
    ExprArithPrime,
    MultExpr,
    MultExprPrime,
    Term,
    AddOp,
    MultOp,
    If,
    IfTail,
    Cond,
    OrCond,
    AndCond,
    AndCondPrime,
    EndCondition,
    SimpleCond,
    Comp,
    While,
    Print,
    Read,
}
