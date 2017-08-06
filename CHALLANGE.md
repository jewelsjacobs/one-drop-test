# API Read Cache

## Specification

At One Drop, we interact with several different vendor APIs to implement services that are important for operating the company and delivering the viewer experience.  Sometimes, the scale of our operations puts an unnecessary burden upon these vendors and in order to alleviate this problem, we implement read caching for the vendor APIs.  Caching reads also provides an opportunity to define custom views of authoritative data, which saves some processing time for downstream services.

In your programming language of choice, using whatever libraries or frameworks make sense, deliver a GitHub API read caching service for One Drop organization data.  The following endpoints should be cached periodically and paginated responses from them should be flattened into a single response payload.  API endpoints outside of this set should be proxied through the service to GitHub (optionally, cache these responses).

For purposes of this exercise you can use parse-community as the github org (if you have a favorite github org use that instead).

```
/

/orgs/parse-community

/orgs/parse-community/members

/orgs/parse-community/repos
```


With the cached data, implement the following custom views for organization repositories:

- Top-N repos by number of forks (/view/top/N/forks)

- Top-N repos by last updated time (/view/top/N/last_updated).

- Top-N repos by open issues (/view/top/N/open_issues).

- Top-N repos by stars (/view/top/N/stars).

- Top-N repos by watchers (/view/top/N/watchers).

## Implementation guidelines:

- The service should be delivered as a git repository with a README.md that provides instructions for building, running and testing.

- The service should read a **GITHUB_API_TOKEN** environment variable to overcome API rate-limit restrictions.

- The service should implement a **/healthcheck endpoint that returns HTTP 200 when the service is ready** to serve API responses.

- The service should accept a **port parameter on startup**, so that we can test with multiple instances locally.

- Given the high query load for a service like this at One Drop, we’d like to be able to _horizontally scale the caches being queried internally without at the same time scaling up our API calls against the Github API. That is, the number of requests to the underlying GitHub API should not increase with the number of caching servers we run._ 
  - How you accomplish that is up to you as long as the desired outcome is achieved. You are free to choose other services such as Redis, Etcd or Consul, for helping to maintain state if needed.

## Design considerations:

Write the code assuming you would have to maintain it.

Deployment considerations (interview preparation):

How would you deploy this service into production at One Drop and maintain it for internal users?
