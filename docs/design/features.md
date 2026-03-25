1. Proper Clean Architecture 
Domain is pure. Repositories are defined as interface in domain, implemented in infra.
   web → app → domain ← infra
2. Result<T> as a proper monad 
flatMap enables a clean pipeline(parse -> validate -> construct) without nested if-blocks.
error accumulation not fail fast.
3. Immutability throughout 
4. JPA annotations are confined in infra. Domain object is not polluted with annotations. 