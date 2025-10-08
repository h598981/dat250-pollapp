<script>
  const API = 'http://localhost:8080'; // dev mode: backend port
  let pollId = 1;
  let options = [];
  let votes = [];
  let selectedOptionId = null;
  let userId = ''; // optional for private polls
  let error = '', info = '';

  async function loadOptions() {
    error=''; info='';
    try {
      const res = await fetch(`${API}/api/polls/${pollId}/options`);
      if (!res.ok) throw new Error(await res.text());
      options = await res.json();
      selectedOptionId = options[0]?.id ?? null;
    } catch (e) {
      error = "Failed to load options: " + e.message;
    }
  }

  async function loadVotes() {
    error=''; info='';
    try {
      const res = await fetch(`${API}/api/polls/${pollId}/votes`);
      if (!res.ok) throw new Error(await res.text());
      votes = await res.json();
    } catch (e) {
      error = "Failed to load votes: " + e.message;
    }
  }

  async function castVote() {
    error=''; info='';
    if (!selectedOptionId) { error="Pick an option first"; return; }
    try {
      const headers = { 'Content-Type': 'application/json' };
      if (userId) headers['X-User-Id'] = userId;
      const res = await fetch(`${API}/api/polls/${pollId}/votes`, {
        method: 'POST',
        headers,
        body: JSON.stringify({ optionId: selectedOptionId })
      });
      if (!res.ok) throw new Error(await res.text());
      info = "✅ Vote submitted!";
      await loadVotes();
    } catch (e) {
      error = e.message || "Failed to vote";
    }
  }
</script>

<div class="card">
  <h2>Vote</h2>
  <div class="row" style="align-items:center;">
    <label>Poll ID <input type="number" min="1" bind:value={pollId} /></label>
    <button on:click={loadOptions}>Load options</button>
    <button on:click={loadVotes}>Load votes</button>
  </div>

  <label>User ID (optional for public polls)
    <input bind:value={userId} placeholder="2" />
  </label>

  <label>Option
    <select bind:value={selectedOptionId}>
      {#each options as o}
        <option value={o.id}>{o.text}</option>
      {/each}
    </select>
  </label>

  <div style="margin-top:10px;">
    <button on:click={castVote}>Vote</button>
  </div>

  {#if error}<p style="color:#ff7b7b;">{error}</p>{/if}
  {#if info}<p style="color:#7bf88a;">{info}</p>{/if}

  <h3>Votes</h3>
  {#if votes?.length}
    <pre>{JSON.stringify(votes, null, 2)}</pre>
  {:else}
    <p>No votes yet.</p>
  {/if}
</div>
