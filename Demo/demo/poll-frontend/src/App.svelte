<script>
  import CreateUserComponent from "./CreateUserComponent.svelte";
  import CreatePollComponent from "./CreatePollComponent.svelte";
  import Vote from "./Vote.svelte";

  // simple tab switcher
  let view = "user"; // "user" | "poll" | "vote"
</script>

<div class="container">
  <header class="card header">
    <h1>Poll App (SPA)</h1>
    <nav class="row">
      <button class:selected={view==='user'} on:click={() => (view = 'user')}>Create User</button>
      <button class:selected={view==='poll'} on:click={() => (view = 'poll')}>Create Poll</button>
      <button class:selected={view==='vote'} on:click={() => (view = 'vote')}>Vote</button>
    </nav>
  </header>

  {#if view === 'user'}
    <CreateUserComponent />
  {:else if view === 'poll'}
    <CreatePollComponent />
  {:else}
    <Vote />
  {/if}
</div>

<style>
  /* light layout so it looks nice out of the box */
  :global(body) { margin: 0; font-family: system-ui, Arial, sans-serif; background:#0b0c10; color:#eaeaea; }
  .container { max-width: 980px; margin: 28px auto; padding: 0 16px; }
  .card { background:#111218; border:1px solid #23242c; border-radius:16px; padding:16px; }
  .header { display:flex; align-items:center; justify-content:space-between; margin-bottom:16px; }
  .row { display:flex; gap:10px; flex-wrap:wrap; }
  button { padding:8px 12px; border-radius:10px; border:1px solid #333; background:#191a20; color:#eaeaea; cursor:pointer; }
  button:hover { background:#1f2027; }
  button.selected { outline:2px solid #6ee7b7; }
</style>
