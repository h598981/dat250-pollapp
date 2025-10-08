<script>
  let username = "";
  let email = "";
  let message = "";

  async function createUser() {
    if (!username || !email) {
      message = "Please fill in all fields.";
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/api/users", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, email })
      });

      if (response.ok) {
        const user = await response.json();
        message = `✅ User created! ID: ${user.id}`;
        username = "";
        email = "";
      } else {
        message = "❌ Failed to create user.";
      }
    } catch (err) {
      message = "⚠️ Server not reachable.";
      console.error(err);
    }
  }
</script>

<div class="create-user">
  <h2>Create User</h2>

  <input placeholder="Username" bind:value={username} />
  <input placeholder="Email" bind:value={email} />
  <button on:click={createUser}>Create</button>

  <p>{message}</p>
</div>

<style>
  .create-user {
    display: flex;
    flex-direction: column;
    width: 300px;
    margin: 2rem auto;
    gap: 0.5rem;
  }
  input, button {
    padding: 0.5rem;
    font-size: 1rem;
  }
  button {
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
  }
  button:hover {
    background-color: #0056b3;
  }
  p {
    text-align: center;
    margin-top: 1rem;
  }
</style>
