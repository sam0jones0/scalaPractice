# Day 1

replicaProgressThreshold lets rollout continue even if not 100% HPA target

Kargo: run any containerisable workload as part of promotion argo step

- Promotion as enforcement boundary, not just delivery …
- Their example didn’t show canary, just env promotion

New Argo v3.3

- Shows live research usage of pods in argo

## 10:40 MCP Auth for Enterprise New Cases with Keycloak

- MCP lets agent speak one language… rather than per-software language

### The problem …

- MCP with OAuth
    - user: resource owner
    - Agentic ai: client
    - Tools are protected resource using auth server
- So the MCP Client and MCP Auth server are registered to IdP
- AI -> auth server -> idp
- You need to do this for EACH MCP server

### Solution

- User id with oidc as before
- see photo
- Lets agentic ai auth to multiple MCP server at once
- not fully implemented in keycloak yet, but on the way..

## 11:15 GraalVM

GraalWASM

Using something called Photon to move between nodes and rust code using some WASM/GraalVM magic I don’t understand

Graalwasm using some web assembly image processing lib written in rust, in a spring boot app.

GraalVM can now output WASM instead of just native compiled

See photo

## Otel Panel

- Check we use compression on Otel protocol as its none by default

## Agentics Day - MCP + Agents

- modern agents need
-
    - agent loop
- code exec
- memory
- connectivity <- focus on this talk

- ### Connectivity

- Skills
    - encapsulate domain knowledge, basic a text file explaining some simple knowledge
    - based on idea on progressive disclosure, i.e. list of skills -> full content of skill
- CLIs
    - unix philosophy for agents, let models access unix like cli skills, they can progressively disclosure by using
      —help functions etc.
    - Unix tools are already composable… limited somewhat to string processing
    - Strong basis in pre-training, i.e. git gh kubectl etc
    - Cons:
        - no way to discover caps ileitis programatically
        - Tied to local exec
        - no standard auth
        - no goverence
        - Giving full shell access?? Bad sec
- MCP - protocol layer for tools
    - remote first
    - standard protocol
    - enterprise grade observability / auth
    - rich type suite,
    - extension mechanism built in
    - On context bloat
        - USE TOOL SEARCH.
            - You replace your list of tools with just a tool search tool
    - On composability, consider MCP is just RPC
        - Give the agent access to REPL with multiple tools available. It can write script to compose many in one as one
          “tool call”, there is no need for the intermediate output of each tool call to enter context if composed this
          way
    - MCP apps provide a web interface…. Something about model returning intractable HTTP…
    - Cross-app Access, a way of auth to multiple MCP servers at once.

## Squeezing perf 14:40

- Node Resource Interface NRI helps tune linux kernel on k8
    - Hook to adjust OCI spec which applies kernel (cgroup) params
- Balloons policy … pools of partitioned by configuration
- E.g. you can set pool of nodes that run processes immediately, before other processes, which will not be preempted and
  will run until blocked…
- Or e.g. linux.mempolicy to specify node selection for memory, based on e.g. proximity to CPU node.
- Applied:
    - Tuning CPU to max frequency reduces wakeup latency...
    - Disable all C-states (power saving) also reduced wakeup latency too ...
    - Scheduling: SCHED_FIFO reduced further...
- Best recipe was, real-time scheduling policy, disable power saving, max frequency
- NRI Balloons see photo
- Takeaway: NRI API helps control low level params not exposed as k8 plugins. There are many NRI plugins for most
  critical workloads
- Not all tricks work all the time, different machines behave differently…

## Beyond Vibecoding - Coach / Player Model - 15:20

Presented by dev from - https://github.com/block/goose

- Today; vibe coding fails on
    - anchoring, loses focus
    - refinement: edge cases handled unevenly
    - completion: open ended without direction
    - complexity: struggles with multi step problems
    - Short feedback loop overwhelms the reviewer and quality degrades
- Increasing autonomous iteration from 5 min to 60 min - to let you focus on something else...
- Their idea is “Adversarial Cooperation”:
    - Player:
        - Reads requirements, writes/executes
        - responds to feedback
        - optimised for production
    - Coach:
        - Validates vs requirements, tests and runs code.
        - Gives feedback
        - optimised for evaluation
- They only read the output of the other, not the whole thinking/conversation
- Some bounds
    - Turn limits, 10 turns seems good
    - each turn starts fresh, no context pollution
    - Good shared doc for reqreuiments - this is key, it is source of truth..
    - Approval gates to complete task
- Context window problem is somewhat solved by fresh turns...
- Natural point for model diversity… different model to produce/review

## AI API Gateways & Semantic Routers

- Normal API gateway is fast, small response and cheap. Ai/LLM traffic is expensive slow and non deterministic
- AI gateways introduce various features of traditional API gateways, like cost management (token level),
- AI API Gateway
    - proxy between apps/model providers, to do rate limiting/cost/caching/otel
- Semantic Router
    - intent classicifaction, model spec routing, cost/quality optimisation, PII detection
    - Basically routes to different models based on prompt contents
- Existing AI Gateways
- Envoy AI Gateway
    - still in dev
    - multi provider access for dev teams… we don’t use multiple models really yet
- Litellm
    - Quick simple multi LLM router under one API key
    - not free enterprise rbac
- Existing Semantic Routers
    - vLLM with plugin "Semantic Router"
        - uses ModernBERT classifier.
    - Kong AI Gateway,
    - kGateway + agent gateway

Notes:

- I don’t think we use self-hosted AI enough for this yet.
- Sounds like something Sky/GST platform should provide anyway, not PRS
- Semantic router makes sense for AI support bot by e.g. query complexity
- Don’t see point using semantic router for anything but model selection.. which itself could just be a tool …
- Also how do you select which model for the router itself (router is not model driven ? … e.g. ModernBERT )

## Web Assembly Without Borders

Run collector in wasm... lets you run otel closer to the edge, i.e. desktop/mobile client

- WASM
    - Binary format
    - Good perf
    - compile from multiple languages
    - Expands devices otel collector can run on
- WASI
    - OS interaction
    - Not stable
- WASM plugins inside collector
    - use any langiage, sandbox
- otelwasm exists... no true paralellism

# Day 2

## 11:15 Game dev at scale

- Microservice meaning single responsbiliy, not single endpoint.
- Using "ease of change" as a decision making metric - how easy is to pivot away from a given technology choice
- Use Redis... or just build a simple in memory repository. Keep it simple stupid.
- Underestimated burst behaviour and **retry storms**
- Simplicity is the uptime strategy. Chose primitives people understand. Do you NEED xyz tech?

## 12:00 Cloud Native Non-Functional Requirements

- Emphasis on loose coupling for micrsoservices
- So far talking about stuff we already do: observability, CI/CD, canary, rollbacks
- Talking about DRY ......

## 14:30 PDB

- Only on eviction API, not delete api etc
- PDB does protect when you update template
- use priorityclass to evict lower priority pods when higher priority pod cannot be scheduled

## 15:15 Schemea Inference and Automation (Otel Weaver)

- They recommend a couple of related talks:
    - Observability by Design
    - Also A practical Blueprint for Evolving Observability Schemeas
- What is observability by design?
    - treat observability like an API
    - Put signals in something like a schemea registry
- OtelWeaver - https://github.com/open-telemetry/weaver / https://opentelemetry.io/blog/2025/otel-weaver/
    - enforces policies via a registry e.g. backward compat
    - Allows declarative metric writing, then generated dashboards/alerts/docs/instrumentation
- They talk about metric name changes not being caught, dashboard breaks, alert breaks ...
- Collected metrics are sent to weaver who validates against (or infers/creates for the first time) registry.yaml
- Once registry.yaml is created "obervability by design starts"
- So then "weaver live check" can do the validation against pre-existing registry.yaml
- Can use registry.yaml to generate otel or prometheus client code
- Open PR in prometheus to make it aware of versioned otel registry stuff

## 16:15 Argo MCP / 10X Developer

- Abstract ArgoCD API behind MCP server...
- introduces argoproj-labs/mcp-for-argocd
- Tools/resources/prompts
    - They provide tools for every argoCD cli/api behaviour
    - No resources are provided
    - prompts are provided (reusable templates e.g. how to diagnose unhealthy app)
- suggested use case: assisted troubleshooting
    - to help with support bottleneck...
    - "argo api is powerful"
- **Use Argo MCP as a reverse proxy to query multiple ArgoCD instances**
    - They mentioned using one argo-cd-mcp instance as a sort of reverse proxy to multiple ArgoCD instances. One entry
      point to query the state of all your apps udner all your argo instances.
- Something about agent skills(?)
- QR code for stuff

# Day 3

## Keynotes

- Saxo service blueprint to consolidate dev/stage/prod infra access PRs, e.g. kafka acls.
- kagent and agentgateway from solo.io
- agent benchmarking - https://aevals.ai/

## 11:00 Generalising k8 controller sharding

- kubernetes-controller-sharding repo allows k8 controllers to be horizontally scalable.

## 11:45 Fluent Bit V5: Pushing the Limits of Observability at Scale

bit of an advert...

- fluentbit is a telemetry pipleine
    - input: otel/prometheus/k8-events
    - processorL LUA scripting, sampling
    - routing: tag/match/regex
    - outputs: splunk,otel prometheus,elastic
- supports otel/prometheus schemea
- Lets you run conditional logic to determine processing/routing decision
- perf stated as faster than otel collector

## 14:15 The Missing Half of Performance Profiling: Understanding Memory in Cloud Native Systems

- Continuous profilers with object introspection current state of art on CPU profiling
    - Is JFR a sampling profiler? (yes?)
- Memory profiling:
    - Allocation sampling - sample probability dictacted by size, i.e. sample 100% >512kb 12.55%>64kb
        - detection heaby alloc
        - e.g. go pprof
    - In use profiling, paired often with allocation samplers: checks for dealloc,
        - detects memory leaks
    - Full heap capture: e.g. jvm heap dump
    - **Object introspection**: newer: introspect object type to avoid heap alloc, i.e. keep container under static
      size.
        - e.g. Meta's OI
        - Note on std:vector vs stdsmall_vector from Meta "folly?" project
        - **How can we avoid heap allocs on JVM / Scala?**
- pprof
    - profiling dataformat in protobuf
    - Examples of pprof tools
        - Parca, sxrapes /pprof endpoints
            - doesnt support jvm
        - Pyroscope - https://pyroscope.io/
            - supports Java
            - push model
        - Pixie
            - doesnt support pprof yet
    - https://github.com/grafana/JPProf
        - can VisualVM, JProfiler, or Async-profiler export to pprof format?
    - Careful of overhead
- **I dont think we in PRS do any continuous profiling?**

## 15:00 Enterprise challenges with MCP Adoption

- See photo for challenges
- Anthropic wont define enterprise features in the spec, they prefer the community to figure it out as "extensions"
- Local agent <--> local MCP communciates via stdio. Suggestion is to avoid this! MCP should be remote.
- enterprise is steering away from API keys for remote MCP auth
- Some people use MCP gateways, which control tracing, auth, authz, lifecycle, observability
    - e.g. agentregistry and agentgateway ... donated to cncf?
- MCP Auth default offering revolves more around OAuth (more user focused or for public SaaS MCP servers, rather than
  enterprise needs)
    - Doesnt provide per tool auth, just "front door" access
    - See photo
    - https://www.solo.io/blog/mcp-authorization-is-a-non-starter-for-enterprise

## 16:45 Keeping the Cloud Afloat with Deterministic Simulation Testing

- etcd MUST be reliable, its the source of truth for k8
- antithesis is introduced as a testing tool...
    - Lets you recreate states you have already seen during tests
    - autonomous search
        - showed example of enumerating mario paths
        - splits timelines (i.e. timelines of system state change events, e.g. a clock advance event). i.e. branches
            - it decides autonomously when to create a new branch, based on many things, e.g. novel state change,
              something "new" happened
        - You can use previous states as starting points
    - determinism
        - its a deterministic VM
- Sounds like fuzzing but with state/time travel
- they gave example of finding a very rare bug, as they could retrospectively see which state changes most increased the
  probability of a given bug appearing.

# Day 4

- Random note: Do we use docker with containerd under the hood or CRI-O ? CRI-O is supposed to be faster or something
  idk

## Kyverno

- Runs in k8 as a "dynamic admissions controller"
- "k8 native", i.e. just yaml managed as k8 resources
- Policy as code, e.g. x k8 object (e.g. pod) must have y attr (e.g. label or version)

## Ctrl-X + Ctrl-V Your Pods - WG Checkpoint Restore in K8

- Historial ideas of usefulness for checkpoint/restore: OOM killer into OOM bumper, security kernel upgrades with
  limited
  fowntime, good for debuggers, profilers
- Their goal is to enable k8-native support for checkpoint/restore
- benefits:
    - accelerating application startup
    - fault tolerance: avoid recomputations, integrations with planned maintenance/spot instances.
    - optimised resource util: load balancing, preemptions of low priority workloads,
- There is a [kubelet-checkpoint-api](https://kubernetes.io/docs/reference/node/kubelet-checkpoint-api/), in beta as of
  k8 v1.3
- KEP-5823 https://github.com/kubernetes/enhancements/issues/5823
- pod-snapshot-controller rst0git
- user -> api -> controller -> kubelet
    - create checkpoint -> get node -> create pod
- Warm start: Create 1-many replicas of pod. Get pod in good state (refresh interval?) and rollout
- pre-alpha
- Alt work - https://gvisor.dev/docs/user_guide/checkpoint_restore/

## The Accidental Platform Team: K8 Operators

- The problem space: Cross-domain knowledge, complex cross-domain workflows. Manual processes and static config.
    - Domain knowledge moved to operators
- Shrink to MVP if pushback against over-automation
- When you abstract away too much at once users of "the old way" may feel a loss of complexity/visibility as negative
  signal

## The Fourth Pillar Arrives: OpenTelemetry Profiles Alpha in Action

- https://github.com/open-telemetry/opentelemetry-ebpf-profiler
- Simplest profile typically collection of stack traces
    - This leads to duplication, e.g. "main" in most if not all traces
- Profiling addresses: Incidents (e.g. sudden CPU increase), costs, performance
- **Otel profiling can link a profile with a trace, e.g. 50percilene latency vs 99percentile latency performace
  comparison**
- Mention of thread timelines and flamescope, which will be useable with the otel profiling format
- [Now in Alpha](https://www.elastic.co/observability-labs/blog/otel-profiling-alpha)
- New profile signal
    - same resource and scope
    - originated from pprof
    - includes a ProfilesDictionary where strings/attributes are stored just once in K:V map
    - Correlatable via "link" to traces, logs etc (spanId / traceId)
- Recommended tool is OpenTelemtry eBPF Profiler (receiver)
- You require one OTel Collector Daemonset per **node** not per pod.
    - all the eBPF stuff runs in kernel
    - You dont need to autoconfigure, it "just works"
    - Custom stack unwindings for Java
- Stated as having negligable overhead (~1% CPU)
- Surfaces bottlenecks in entire syustem , not just code you own
- Frictionless deployment, no need to restart pod etc
- Run the reciever separate to other receieces as it has elevated privelges
- Very cool showing profile comparing before after release, just side by side profile diff (of an LLM generated
  bug/outage)