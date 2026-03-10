# TechOrda Module 9 — Test Automation

Java-based Cucumber test automation framework.
**Stack:** Java 11, Cucumber, JUnit 5, Selenium, Gradle.

---

## Prerequisites

- Java 11+
- Google Chrome installed
- EPAM credentials with access to Microsoft Authenticator (for Assets tests)

---

## Authentication

> **Important:** Tests for the **Assets** page require an active EPAM session.

Automating MFA (Microsoft Authenticator) is not feasible, so authentication must be done **manually** before running the tests. The supported approach is to start Chrome in debug mode, log in once, and then run tests against that session.

**Step 1: Start Chrome in debug mode**

```bash
# macOS / Linux:
./QUICK_START.sh

# Windows:
QUICK_START.bat
```

**Step 2: Log in manually**

In the opened Chrome, navigate to the site, click **Sign In**, enter your EPAM credentials, and confirm via Microsoft Authenticator on your phone.

**Step 3: Run the tests** — see [Running Tests](#running-tests) below.

> Only **Assets** tests (`assets.feature`) require authentication. All other tests (Solutions, Guides, About) run without it.

---

## Test Coverage

The assignment required a minimum of **10 test scenarios**. This implementation covers **5 pages with 3 scenarios each — 15 scenarios total**.

| Page      | Feature file         | Scenarios |
|-----------|----------------------|-----------|
| About     | `about.feature`      | 3         |
| Assets    | `assets.feature`     | 3         |
| Blog      | `blog.feature`       | 3         |
| Guides    | `guides.feature`     | 3         |
| Solutions | `solutions.feature`  | 3         |
| **Total** |                      | **15**    |

---

## Running Tests

### Standard mode — for reviewers and CI/CD

Creates a fresh Chrome session each time:

```bash
./gradlew test
```

### Development mode — reuse Chrome session

Connects to an already-open Chrome instance. Required for running Assets tests (see [Authentication](#authentication) for setup steps).

```bash
./gradlew test -Dchrome.reuse=true
```

Chrome will stay open between test runs. Authentication will be preserved.

#### Custom debug port

```bash
./gradlew test -Dchrome.reuse=true -Dchrome.debug.port=9223
```

#### Run a specific feature file

```bash
./gradlew test -Dchrome.reuse=true -Dcucumber.features=src/test/resources/org/example/features/guides.feature
```

---

## IntelliJ IDEA Setup

To run tests in reuse mode from the IDE, add the following VM option to your Run Configuration:

```
-Dchrome.reuse=true
```

**Recommended setup — two configurations:**

| Configuration  | VM Options            | Use case                      |
|----------------|-----------------------|-------------------------------|
| Tests (Dev)    | `-Dchrome.reuse=true` | Fast development, no re-auth  |
| Tests (Review) | *(empty)*             | Clean session for reviewers   |

**Steps:**
1. `Run` → `Edit Configurations...`
2. Select or create a Cucumber / JUnit configuration
3. Click `Modify options` → `Add VM options`
4. Enter `-Dchrome.reuse=true`
5. Save with `Apply` / `OK`

---

## Troubleshooting

**"Could not connect to debugger"**
- Make sure Chrome is running with `--remote-debugging-port=9222`
- Verify by opening `http://localhost:9222` in another browser — you should see the list of open tabs
- Check if the port is already in use:
  ```bash
  # macOS/Linux
  lsof -i :9222
  # Windows
  netstat -ano | findstr :9222
  ```
- To free the port:
  ```bash
  pkill -f "chrome.*remote-debugging-port=9222"
  ```

**Browser closes after tests**
- Make sure `-Dchrome.reuse=true` is passed
- Check logs for: `Keeping Chrome instance open (reuse mode)`

**Tests don't see authentication**
- Ensure you logged in using the same Chrome instance that was started with `--remote-debugging-port=9222`
- Verify the `--user-data-dir` path matches