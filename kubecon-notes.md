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

- 