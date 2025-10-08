<script>
  // Dev: talk to Spring on 8080. Later for prod, change to '' (relative).
  const API = 'http://localhost:8080';

  // form state
  let ownerUserId = 1;
  let question = '';
  let isPublic = true;
  let limitOnePerUser = false;

  // default time window: open now-1min .. +7 days
  let publishedAt = new Date(Date.now() - 60_000).toISOString();
  let deadlineAt  = new Date(Date.now() + 7*24*60*60*1000).toISOString();

  // options list
  let options = ['Yes', 'No'];
  // only for private polls: comma-separated ids
  let invitedUserIds = '2';

  let creating = false;
  let created = null;
  let error = '';

  function addOption() {
    options = [...options, ''];
  }
  function removeOption(i) {
    options = options.filter((_, idx) => idx !== i);
  }

  async function submit() {
    error = '';
    created = null;

    // basic validation
    if (!question.trim()) { error = 'Question is required'; return; }
    const cleanedOptions = options.map(o => o.trim()).filter(Boolean);
    if (cleanedOptions.length < 2) { error = 'Provide at least two options'; return; }

    const payload = {
      ownerUserId: Number(ownerUserId),
      question: question.trim(),
      isPublic,
      limitOnePerUser,
      publishedAt,
      deadlineAt,
      options: cleanedOptions,
      invitedUserIds: isPublic
        ? []
        : invitedUserIds.split(',').map(s => s.trim()).filter(Boolean).map(Number)
    };

    creating = true;
    try {
      const res = await fetch(`${API}/api/polls`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      });
      if (!res.ok) throw new Error(await res.text());
      created = await res.json();

      // optional: reset light parts of the form
      // question = ''; options = ['Yes','No'];
    } catch (e) {
      error = e.message || 'Failed to create poll';
    } finally {
      creating = false;
    }
  }
</script>

<div class="card">
  <h2>Create Poll</h2>

  <div class="row">
    <label>Owner user id
      <input type="number" bind:value={ownerUserId} min="1" />
    </label>

    <label>Public?
      <select bind:value={isPublic}>
        <option value={true}>Yes (anonymous allowed)</option>
        <option value={false}>No (invited users only)</option>
      </select>
    </label>

    <label>Limit one vote/user
      <select bind:value={limitOnePerUser}>
        <option value={false}>No</option>
        <option value={true}>Yes</option>
      </select>
    </label>
  </div>

  <label>Question
    <input bind:value={question} placeholder="Favorite language?" />
  </label>

  <div class="row">
    <label>Published at (ISO)
      <input bind:value={publishedAt} />
    </label>
    <label>Deadline at (ISO)
      <input bind:value={deadlineAt} />
    </label>
  </div>

  {#if !isPublic}
    <label>Invited user ids (comma separated)
      <input bind:value={invitedUserIds} placeholder="2,3" />
    </label>
  {/if}

  <hr />

  <h3>Options</h3>
  {#each options as opt, i}
    <div class="row" style="align-items:center;">
      <input bind:value={options[i]} placeholder={"Option " + (i+1)} style="flex:1;" />
      <button type="button" on:click={() => removeOption(i)}>Remove</button>
    </div>
  {/each}
  <div style="margin-top:8px;">
    <button type="button" on:click={addOption}>+ Add option</button>
  </div>

  <div style="margin-top:12px;">
    <button on:click|preventDefault={submit} disabled={creating}>
      {creating ? 'Creating…' : 'Create poll'}
    </button>
  </div>

  {#if error}<p style="color:#ff8686; margin-top:8px;">{error}</p>{/if}
  {#if created}
    <p class="badge" style="margin-top:8px;">Created poll id: {created.id}</p>
    <pre>{JSON.stringify(created, null, 2)}</pre>
  {/if}
</div>

<style>
  .card { background:#111218; border:1px solid #23242c; border-radius:12px; padding:16px; }
  .row { display:flex; gap:12px; flex-wrap:wrap; }
  input, select, button { padding:8px 10px; border-radius:8px; border:1px solid #333; background:#191a20; color:#eaeaea; }
  button { cursor:pointer; }
  h2 { margin-top:0; }
</style>
