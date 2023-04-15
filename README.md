<div align="center">
   <img src=".github/assets/images/icon.png" height="120px" alt="grind.xyz">

   <h3>Cp.Orderly</h3>
   <p>An Expieremental, Clean, Extensible, Event-Driven & Scalable <br> Goods Ordering Software Architecture</p>

   <div style="">
      <img src="https://github.com/humamaboalraja/cp.orderly/actions/workflows/build.yaml/badge.svg" alt="Cp.Orderly Build status" />
         <a href="https://sonarcloud.io/summary/new_code?id=humamaboalraja_grind.xyz" rel="Coverage">
         <img src="https://sonarcloud.io/api/project_badges/measure?project=humamaboalraja_cp.orderly&metric=coverage" alt="Coverage" />
      </a>
      <a href="https://sonarcloud.io/summary/new_code?id=humamaboalraja_cp.orderly" rel="Security rating">
         <img src="https://sonarcloud.io/api/project_badges/measure?project=humamaboalraja_cp.orderly&metric=security_rating" alt="Security rating" />
      </a>
      <img src="https://img.shields.io/github/license/humamaboalraja/cp.orderly?color=434158" alt="license" />
   </div>

</div>


---

<br>

## 1.  Modules/Services
All the following modules can be deployed, and managed seperately but they were put the same repository for the sake of convinience.
ID  | Name | Description
----|----|----
[CS](/customer/) | Customer Service | Customers functionalities
[SHS](./shop/) | Shop Service | Shop functionalities
[OS](./order/) | Order service Service | Orders management functionalities
[PS](./payment/) | Payment service | Payments functionalities
[IM](./infrastructure/) | Infrastructure module | Contains concrete infrastructure client's implementations and consistency and long running transactions handlers
[COM](./common/) | Commons module | contains re-usable and generic domain entities, events, exceptions, value objects and repositories
[LOC](./local/) | Local development | consists of all the needed to-be-provisioned environments for local development porposes

<br>


## 2. Documentation
For more detailes about how the overall system is interacting together, refer to `1.Architecture.md` in [`docs`](./docs/) where the system's documentation resides. Service specific implementation and setup details can be found in the the respective service's `README.md`.



<br>


## 3. Building

### 3.1 Setting up Kafka's cluster
...

### 3.2 Building from source

### 3.3 Building from source

<br>


## 4. Contribution Guidelines
### 4.1 Commits Guidelines
To ease up the process of versioning the software based on the kind of changes/features being applied/added, this repository is using [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/) for its git commit messages.

The following keywords can be used:

```bash
feat, fix, test, build, perf, docs, refactor / ref, chore
```
Commit keywords can include one or more scopes for the specic module being modified. E.g.

```bash
feat(CS, SHS): ..Commit message goes here..
ref(OS): ...
```

### 4.2 Branches rules
Every new feature/improvement is push with a new branch name is prefixed with its purpose of creation preferable `conventional-commit-keyword/brief-title`.  
`Main` branch is protected and requires pull request and an approval of at least one reviewer. branches are squashed and rebased.

### 4.3 CI Checks
Pull requests can't be merged without meeting the requirements mentioed above, plus the linting, testing, and building jobs which should be tested during development.

For checking and applying the linting changes the project is relying on [Spotless](https://github.com/diffplug/spotless).

### 4.3 Re-usable module builds compponents
...
<br>



### Literature, references & resources
- [The Clean Architecture - Clean Coder Blog. (2012, August 13)](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) - [Book](https://www.oreilly.com/library/view/clean-architecture-a/9780134494272/)
- [Vernon, V. (2016). Domain-driven Design Distilled. Addison-Wesley Professional.](https://www.oreilly.com/library/view/domain-driven-design-distilled/9780134434964/)
- [Vernon, V. (2013). Implementing Domain-driven Design. Pearson Education.](https://www.oreilly.com/library/view/implementing-domain-driven-design/9780133039900/)
- [Kleppmann, M. (2017). Designing Data-intensive Applications: The Big Ideas Behind Reliable, Scalable, and Maintainable Systems.](https://www.oreilly.com/library/view/implementing-domain-driven-design/9780133039900/)
- [Evans, E., & Evans, E. J. (2004). Domain-driven Design: Tackling Complexity in the Heart of Software. Addison-Wesley Professional.](https://www.oreilly.com/library/view/domain-driven-design-tackling/0321125215/)
- [Garcaa-Molina.H, Salem. K. - Sagas](https://www.cs.cornell.edu/andru/cs711/2002fa/reading/sagas.pdf)
- [Cockburn, A. (n.d.). Hexagonal architecture. Alistair Cockburn](https://alistair.cockburn.us/hexagonal-architecture/)
- [Newman, S. (2019). Building Microservices: Designing Fine-Grained Systems. O’Reilly Media.](https://www.oreilly.com/library/view/building-microservices-2nd/9781492034018/)
- [Cockburn, A. (2001). Writing Effective Use Cases. Agile Software Development Series.](https://books.google.de/books/about/Writing_Effective_Use_Cases.html?id=VKJQAAAAMAAJ&redir_esc=y)
- [H. (2020). DDD, Hexagonal, Onion, Clean, CQRS, ... How I put it all together. ](https://herbertograca.com/2017/11/16/explicit-architecture-01-ddd-hexagonal-onion-clean-cqrs-how-i-put-it-all-together/)
- [Ford, N., Richards, M., Sadalage, P., & Dehghani, Z. (2021). Software Architecture: the Hard Parts](https://www.oreilly.com/library/view/software-architecture-the/9781492086888/)
- [Martinez, P. (2022, January 6). Hexagonal Architecture, there are always two sides to every story. Medium.](https://medium.com/ssense-tech/hexagonal-architecture-there-are-always-two-sides-to-every-story-bc0780ed7d9c)


---
