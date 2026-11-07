# 你滷味 · Your Lou Mei

**A tea egg that lives in a braising pot on your desktop.**

Read this in [繁體中文](README.md).

---

## The idea

Most desktop pets just sit there. This one has a stove.

The pot's temperature is the whole mechanic: leave for a while and the broth cools, the steam
dies down, the egg gets quiet. Come back and it boils again. Attention is the only resource the
app asks for, and it asks for it quietly.

The character is a tea egg named 阿茶 (A-Cha). You can rename him.

## Status

**Early development.** The repository currently contains project scaffolding only — there is no
application code yet. Everything below describes intent, not shipped behaviour.

## Planned features

- Transparent, always-on-top window, with platform-correct behaviour on Windows and macOS
- Layered 2D animation — pot, broth, egg and steam composited at runtime, never flattened
- Idle behaviours, hover, drag, sleep, and a small set of reactions
- Soft, sparse sound effects
- AI conversation through any OpenAI-compatible endpoint, including local models
- Roaming across the desktop
- Expeditions away from the pot, with a light progression loop

## Tech stack

Tauri 2 · React · TypeScript · Vite

## Platforms

| | |
|---|---|
| Windows | 10, 11 |
| macOS | 12 and later, Intel and Apple silicon |

Distributed as direct downloads (NSIS installer and DMG). Not distributed through the Mac App
Store — the transparent-window implementation on macOS uses an API that the store does not
permit.

## Privacy

This is a small app that sits next to your work all day, so the boundaries are explicit:

- Conversations, save data and settings stay on your machine. No account, no sync, no server.
- No telemetry and no analytics of any kind.
- **The app does not read what you type.** To know whether you are at the computer, it asks the
  operating system for the system idle time — a single number, no content, no permission prompt.
  A global keyboard hook would have been easier and is deliberately not used.
- API keys are stored locally and are never transmitted anywhere except the endpoint you
  configure.

## Roadmap

- [ ] Application shell: transparent window, tray, autostart
- [ ] Companion behaviour and animation system
- [ ] AI conversation with a streaming speech bubble
- [ ] Roaming across the desktop
- [ ] Expeditions and progression
- [ ] Signed installers for both platforms

## Building

Not yet available. Once the first build lands, the requirements will be Node 20+, a stable Rust
toolchain, and the platform toolchain (MSVC build tools on Windows, Xcode Command Line Tools on
macOS).

## Contributing

This is a personal project. Issues are welcome; pull requests are not being accepted at this
time.

## License

To be determined.
