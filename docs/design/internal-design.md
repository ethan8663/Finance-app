-- Domain object --
Transaction class 
private attributes 
LocalDate recordAt
Money money
Type type (enum)
Category category (enum)
String payer
String payee
String note 

ctor - takes every attribute, null checks except note(nullable)

toString

RecurringTransaction class
private attributes
LocalDate recordAt
Money money
Type type (enum)
Category category
String payer
String payee
String note 
TransactionDate transactionDate (enum)
LocalDate startDate
LocalDate endDate 

ctor - takes every attribute, null checks except note and endDate

toString

-- Value object -- 
Money class 
private final attributes: BigDecimal amount
private ctor
static of(String money): Result<Money>
parse money into BigDecimal
should be positive
return Money

-- Application -- 
TransactionDraft class 
takes every attribute as String via ctor
getters

RecurringTransactionDraft class
takes every attribute as String via ctor 
getters

TransactionService class
private final attributes: time, repo
takes attributes via ctor

one public method create(TransactionDraft td): Result<Transaction>
parse and validate transaction draft attributes 
If validation pass, construct transaction object and insert into db. Otherwise, return accumulated error messages enclosed in Result.

private methods for parsing
parseDate(String date) : LocalDate
parseType(String type) : Type

private methods for validation 
validateDate(time, LocalDate date)
date should not be in the future

validateCategory(repo, String type, String cat)
based on the type, check for existence in db

validateName for payer and payee
max length 10 characters

validateNote
max length 20 characters 

RecurringTransactionService class 
same 

parseTransactionDate(String td): TransactionDate

validateStartDate(LocalDate sd)
validateEndDate(LocalDate ed)

Result class 

MenuService class 
prints menu
read user choice
routes to right handler 
loops until exit 

-- Infrastructure --
TransactionRepository - interface
CategoryRepository - interface

JdbcTransactionRepository class
JdbcRecurringTransactionRepository class
JdbcCategoryRepository class
ConnectionProvider class


